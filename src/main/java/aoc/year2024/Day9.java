package aoc.year2024;

import lombok.Data;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

public class Day9 {
    public static void main(String[] args) throws URISyntaxException, IOException {
        String input = Utils.getString("/aoc.year2024/day9.txt");
        LinkedList<String> linkedInput = new LinkedList<>(Arrays.stream(input.split("")).toList());
        LinkedList<Block> blockList = new LinkedList<>();
        int idCount = 0;
        for (int i = 0; i < linkedInput.size(); i++) {
            if (i % 2 == 0) {
                // id a file
                blockList.add(new Block(idCount, Integer.parseInt(linkedInput.get(i))));
                idCount++;
            } else {
                blockList.add(new Block(Integer.parseInt(linkedInput.get(i))));
            }
        }

        Integer fileToMove;
        while( (fileToMove = getNextFileToMove(blockList)) != null ) {
            Block lastBlockWithFile = blockList.get(fileToMove);
            ListIterator<BlockBit> li = lastBlockWithFile.getBlockBits().listIterator(lastBlockWithFile.getBlockBits().size());
            BlockBit lastBit = null;
            while (li.hasPrevious() && lastBit == null) {
                BlockBit tmp = li.previous();
                if (tmp.getFileId() != null) {
                    lastBit = tmp;
                }
            }
            
            int movingFileId = lastBit.getFileId();
            // find first empty
            
            FULL: for (Block b : blockList) {
                for (BlockBit bb: b.getBlockBits() ) {
                    if ( bb.getFileId() == null ) {
                        System.out.println("Moving: " + fileToMove);
                        bb.setFileId(movingFileId);
                        lastBit.setFileId(null);
                        break FULL;
                    }
                }
            }
        }
        long checksum = 0;
        long fieldCounter = 0;
        FULL: for (Block b : blockList) {
            for (BlockBit bb: b.getBlockBits() ) {
                if ( bb.getFileId() != null ) {
                    checksum += (fieldCounter * bb.getFileId());
                    fieldCounter++;
                }
            }
        }
        System.out.println("checksum = " + checksum);
    }

    private static Integer getNextFileToMove(LinkedList<Block> blockList) {
        boolean isFreeBefore = false;
        Integer lastIndex = null;
        for (int i = 0; i < blockList.size(); i++) {
            Block currentBlock = blockList.get(i);
            for (BlockBit bit: currentBlock.getBlockBits()) {
                if (bit.getFileId() == null) {
                    isFreeBefore = true;
                } else {
                    lastIndex = i;
                }
            }
        }
        if ( !isFreeBefore ) {
            return null;
        }
        return lastIndex;
    }

    @Data
    public static class Block {
        private LinkedList<BlockBit> blockBits;

        public Block(Integer id, Integer blockSize) {
            blockBits = new LinkedList<>();
            for (int i = 0; i < blockSize; i++) {
                blockBits.add(new BlockBit(id));
            }
        }

        public Block(Integer blockSize) {
            this(null, blockSize);
        }
    }

    @Data
    public static class BlockBit {
        private Integer fileId;

        public BlockBit(Integer fileId) {
            this.fileId = fileId;
        }
    }
}

