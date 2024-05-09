import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;


import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.text.PDFTextStripper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws IOException {
//        pdfToJson();

//        pdfToPng();
    }

    private static void filenameUp() {
        String directoryPath = "src/main/resources/images"; // 파일들이 있는 디렉토리 경로
        for (int i = 963; i <= 967; i++) {
            File oldFile = new File(directoryPath, i + ".png");
            File newFile = new File(directoryPath, (i - 1) + ".png");
            if (oldFile.exists()) {
                oldFile.renameTo(newFile);
            }
        }
    }

    private static void pdfToPng() throws IOException {
        File file = new File("src/main/resources/driver_test.pdf");
        PDDocument document = Loader.loadPDF(file);
        int imageNumber = 679;
        for (PDPage page : document.getPages()) {
            PDResources resources = page.getResources();
            for (COSName xObjectName : resources.getXObjectNames()) {
                PDXObject xObject = resources.getXObject(xObjectName);
                if (xObject instanceof PDImageXObject) {
                    PDImageXObject image = (PDImageXObject) xObject;
                    BufferedImage bImage = image.getImage();
                    ImageIO.write(bImage, "PNG", new File("src/main/resources/images/" + imageNumber + ".png"));
                    imageNumber++;
                }
            }
        }
    }

    private static void pdfToJson() {
        String text = "";
        try {
            // PDF 파일 로드

            System.out.println(System.getProperty("user.dir"));

            File file = new File("src/main/resources/driver_test.pdf");
            PDDocument document = Loader.loadPDF(file);


            // PDFTextStripper를 사용하여 텍스트 추출
            PDFTextStripper pdfStripper = new PDFTextStripper();
            text = pdfStripper.getText(document);



            // 추출된 텍스트 출력
//            System.out.println(text);

            // 문서 닫기
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<String> list = new ArrayList<>();
        List<Quiz> quizList = new ArrayList<>();

        for (int i = 1; i <= 100; i++) {
//            System.out.println(i);
            String startText = i + ". ";
            int fromIdx = text.indexOf(i + ". ") + startText.length();
            int endIdx = text.indexOf((i + 1) + ". ");
            String quiz = text.substring(fromIdx, endIdx < 0 ? text.length() : endIdx);
            list.add(quiz);



//            String question = quiz.substring(0, quiz.indexOf("?") + 1);
//            Pattern pattern = Pattern.compile("■ 정답\\s*:\\s*");
//            Matcher matcherEnd = pattern.matcher(quiz);
//
//            String choices = quiz.substring(quiz.indexOf("?") + 1, matcherEnd.start());
//            Matcher matcherStart = matcherEnd;
//            pattern = Pattern.compile("■ 해설\\s*:\\s*");
//            matcherEnd = pattern.matcher(quiz);
//
//            String answer = quiz.substring(matcherStart.end(), matcherEnd.start());
//            matcherStart = matcherEnd;
//
//            String explanation = quiz.substring(matcherStart.end());
//            Quiz quizClass = Quiz.builder()
//                    .question(question)
//                    .choices(choices)
//                    .answer(answer)
//                    .explanation(explanation)
//                    .build();
//            quizList.add(quizClass);



        }

//        for (int i = 0; i < list.size(); i++) {
//            System.out.println(i + ":\n" + list.get(i));
//        }


        // ObjectMapper 객체 생성
        ObjectMapper mapper = new ObjectMapper();

        // JSON 파일로 저장
        try {
            mapper.writeValue(new File("src/main/resources/driver_test.json"), list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
