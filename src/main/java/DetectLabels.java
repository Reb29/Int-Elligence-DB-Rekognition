import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.List;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.AmazonRekognitionException;
import com.amazonaws.services.rekognition.model.DetectLabelsRequest;
import com.amazonaws.services.rekognition.model.DetectLabelsResult;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.Label;
import com.amazonaws.util.IOUtils;
import com.amazonaws.services.rekognition.model.Instance;

public class DetectLabels {
    public static void main(String[] args) throws Exception {
        int count = 0;

        String path = "src/main/resources/Lot A.png";

        ImageSplitter splitter = new ImageSplitter(path);
        List<String> filePaths = splitter.split();

        ByteBuffer imageBytes;
        //Multiple
        for (String photo : filePaths) {
            try (InputStream inputStream = new FileInputStream(new File(photo))) {
                imageBytes = ByteBuffer.wrap(IOUtils.toByteArray(inputStream));
            }

            AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder.defaultClient();

            DetectLabelsRequest request = new DetectLabelsRequest().withImage(new Image().withBytes(imageBytes))
                    .withMaxLabels(200).withMinConfidence(40F);

            try {
                DetectLabelsResult result = rekognitionClient.detectLabels(request);
                List<Label> labels = result.getLabels();

                for (Label label : labels) {
                    if (label.getName().equals("Car")) {
                        List<Instance> instances = label.getInstances();
                        count = count + instances.size();
                        System.out.println(count);
                    }

                }
            } catch (AmazonRekognitionException e) {
                e.printStackTrace();
            }
        }


    }
}