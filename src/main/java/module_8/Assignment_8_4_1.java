package module_8;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import Log.Logger;

public class Assignment_8_4_1 {

    // Magnet link example (bittorent v2):
    // magnet:?xt=urn:btmh:1220caf1e1c30e81cb361b9ee167c4aa64228a7fa4fa9f6105232b28ad099f3a302e&dn=bittorrent-v2-test

    private final int PIECE_SIZE = 16 * 1024;

    public void execute(String[] params) {
        if (params.length != 1) {
            Logger.log("Wrong params! It should be file or directory name.");
            return;
        }

        String fileName = params[0];

        // The plan
        // 1. Break down a file into 16kb pieces;
        // 2. If last piece size is less than 16bk, fill the rest of it by zero bytes
        // 3. It must be even number of pieces; if it's not duplicate the last piece
        // 4. Hash pieces; generate SHA-256 for each piece
        // 5. Calculate hash of each pair of pieces; do it recursively and get top hash
        // 6. Generate magnet link

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            ArrayList<byte[]> piecesHashes = new ArrayList<>();

            FileInputStream inputStream = new FileInputStream(fileName);
            byte[] buffer = new byte[PIECE_SIZE];

            int bytesAmountRead = 0;
            while ((bytesAmountRead = inputStream.read(buffer)) != -1) {
                Logger.log("Bytes amount read: %s", bytesAmountRead);

                byte[] binaryHash = digest.digest(buffer);
                piecesHashes.add(binaryHash);

                String result = binaryToHex(binaryHash);
                Logger.log("Hexadecimal hash: %s", result);
            }

            inputStream.close();

            Logger.log("Pieces amount: %s", piecesHashes.size());
            List<byte[]> complementedPiecesHashes = duplicateLastPieceHashIfNecessary(piecesHashes);
            Logger.log("Pieces amount after completion: %s", complementedPiecesHashes.size());

            byte[] topHash = getTopHash(complementedPiecesHashes, digest);
            String topHashHex = binaryToHex(topHash);
            Logger.log("Top hash hex: %s", topHashHex);

            String magnetLink = generateMagnetLink(topHashHex, fileName);
            Logger.log("Magnet link: %s", magnetLink);

        } catch (IOException | NoSuchAlgorithmException e) {
            Logger.log("Error while reading a file %s, error %s", fileName, e.getMessage());
            e.printStackTrace();
        }
    }

    private String binaryToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString();
    }

    private byte[] getTopHash(List<byte[]> piecesHashes, MessageDigest digest) {
        if (piecesHashes.size() == 1) {
            return piecesHashes.get(0);
        }

        List<byte[]> complementedPiecesHashes = duplicateLastPieceHashIfNecessary(piecesHashes);
        List<byte[]> squeezedPiecesHashes = new ArrayList<>();

        byte[] concat = new byte[2 * PIECE_SIZE];
        for (int i = 0; i < complementedPiecesHashes.size() - 1; i = i + 2) {
            byte[] first = complementedPiecesHashes.get(i);
            byte[] second = complementedPiecesHashes.get(i + 1);
            concat = concatHashes(first, second);

            byte[] binaryHash = digest.digest(concat);
            squeezedPiecesHashes.add(binaryHash);
        }

        return getTopHash(squeezedPiecesHashes, digest);
    }

    private List<byte[]> duplicateLastPieceHashIfNecessary(List<byte[]> piecesHashes) {
        if (piecesHashes.size() % 2 != 0) {
            byte[] lastPieceHash = piecesHashes.get(piecesHashes.size() - 1);
            piecesHashes.add(lastPieceHash);
        }

        return piecesHashes;
    }

    private byte[] concatHashes(byte[] first, byte[] second) {
        //assume that first and second have equal length

        int l = first.length;
        byte[] result = new byte[2 * l];

        System.arraycopy(first, 0, result, 0, l);
        System.arraycopy(second, 0, result, l, l);

        return result;
    }

    private String generateMagnetLink(String topHashHex, String fileName) {
        StringBuilder sb = new StringBuilder();
        sb.append("magnet:?xt=urn:btmh:");
        sb.append(topHashHex);
        sb.append("&dn=");
        sb.append(fileName);

        return sb.toString();
    }
}
