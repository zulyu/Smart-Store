package com.se300.ledger.model;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

/**
 * MerkleTree Implementation
 *
 * @author  Nikhil GOYAL
 * @code https://github.com/goyalnikhil02/MerkleTree/blob/master/src/com/example/MerkleTrees.java
 */
public class MerkleTrees {

    // A list of transaction
    List<String> txList;

    // Merkle Root
    String root;


    public MerkleTrees(List<String> txList) {
        this.txList = txList;
        root = "";
    }

    public void merkle_tree() {
        List<String> tempTxList = new ArrayList<String>();

        for (int i = 0; i < this.txList.size(); i++) {
            tempTxList.add(this.txList.get(i));
        }

        List<String> newTxList = getNewTxList(tempTxList);
        while (newTxList.size() != 1) {
            newTxList = getNewTxList(newTxList);
        }

        this.root = newTxList.get(0);
    }

    private List<String> getNewTxList(List<String> tempTxList) {

        List<String> newTxList = new ArrayList<String>();
        int index = 0;
        while (index < tempTxList.size()) {
            // left
            String left = tempTxList.get(index);
            index++;

            // right
            String right = "";
            if (index != tempTxList.size()) {
                right = tempTxList.get(index);
            }

            // sha2 hex value
            String sha2HexValue = getSHA2HexValue(left + right);
            newTxList.add(sha2HexValue);
            index++;

        }

        return newTxList;
    }

    /**
     * Return hex string
     *
     * @param str
     * @return
     */
    public String getSHA2HexValue(String str) {
        byte[] cipher_byte;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(str.getBytes());
            cipher_byte = md.digest();
            StringBuilder sb = new StringBuilder(2 * cipher_byte.length);
            for (byte b : cipher_byte) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * Get Root
     *
     * @return
     */
    public String getRoot() {
        return this.root;
    }

}
