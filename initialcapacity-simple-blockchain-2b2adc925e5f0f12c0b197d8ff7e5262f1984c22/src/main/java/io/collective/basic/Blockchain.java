package io.collective.basic;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class Blockchain {
    private List<Block> chain;

    public Blockchain() {
        this.chain = new ArrayList<>();
    }

    public boolean isEmpty() {
        return this.chain.isEmpty();
    }

    public void add(Block block) {
        this.chain.add(block);
    }

    public int size() {
        return this.chain.size();
    }

    public boolean isValid() {
        try {
            if (isEmpty()) {
                return true;
            }

            // Check a chain of one
            if (size() == 1) {
                System.out.println("Hash of single block: " + chain.get(0).getHash());
                return isMined(chain.get(0));
            }

            // Check a chain of many
            for (int i = 1; i < chain.size(); i++) {
                Block current = chain.get(i);
                Block previous = chain.get(i - 1);

                System.out.println("Hash of current block: " + current.getHash());
                System.out.println("Hash of previous block: " + previous.getHash());

                if (!isMined(current) || !current.getPreviousHash().equals(previous.getHash())) {
                    return false;
                }
            }

            return true;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Block mine(Block block) {
        try {
            Block mined = new Block(block.getPreviousHash(), block.getTimestamp(), block.getNonce());

            while (!isMined(mined)) {
                mined = new Block(mined.getPreviousHash(), mined.getTimestamp(), mined.getNonce() + 1);
            }
            return mined;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean isMined(Block minedBlock) {
        try {
            return minedBlock.getHash().startsWith("00");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false;
        }
    }
}