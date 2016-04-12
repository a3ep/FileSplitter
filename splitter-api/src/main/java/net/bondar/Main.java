package net.bondar;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 */
public class Main {
    String fileDest = "/home/vsevolod/Загрузки/111/SkillsUpWebApp-master.zip";
    String chunkSize = "1 M";
    List<String> partFileNames = new ArrayList<>();
    public static void main(String[] args) throws IOException {
        Main main = new Main();
        System.out.println("start split");
        main.split(main.fileDest, main.chunkSize);
        System.out.println("finish split");

        try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))){
            if(!br.readLine().equals("merge")) System.exit(0);
        }
        System.out.println("start merge");
        main.merge("/home/vsevolod/Загрузки/111/SkillsUpWebApp-master.zip_part_001");
        System.out.println("finish merge");
    }

    public void split(String fileDest, String chunkSize) throws IOException {
        int partCounter = 1;
        File file = new File(fileDest);
        int partSize = Integer.parseInt(chunkSize.substring(0, chunkSize.indexOf("M")).replace(" ", "")) * 1024 * 1024;
        byte[] buffer = new byte[partSize];

        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
            String name = file.getName();
            int tmp;

            while ((tmp = bis.read(buffer)) > 0) {
                File partFile = new File(file.getParent(), name + "_part_" + String.format("%03d", partCounter++));
                partFileNames.add(partFile.getName());
                try (FileOutputStream fos = new FileOutputStream(partFile)) {
                    fos.write(buffer, 0, tmp);
                }
            }
        }
    }

    public void merge(String partDest) throws IOException {
        List<File> partFileList = getPartsList(partDest);
        File completeFile = new File(partFileList.get(0).getParent(), partFileList.get(0).getName().substring(0, partFileList.get(0).getName().indexOf("_")));
        try(BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(completeFile))){
            for(File file:partFileList){
                Files.copy(file.toPath(), bos);
            }
        }
    }
    public List<File> getPartsList(String dest){
        File partFile = new File(dest);
        String partName = partFile.getName();
        String destName = partName.substring(0, partName.indexOf("_part_"));
        File file = partFile.getParentFile();
        File[] files = file.listFiles((File dir, String name) -> name.matches(destName + ".+\\_\\d+"));
        Arrays.sort(files);
        return Arrays.asList(files);
    }
}
