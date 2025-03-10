package book.data.service.service.content;

import book.data.service.service.file.FileService;
import book.data.service.service.time.TimeService;
import java.io.IOException;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FfmpegService {

    public static final String COMBINE_AUDIO_VIDEO_COMMAND = "ffmpeg -loop 1 -framerate 1 -i {imageFileName} -i {audioFileName} -c:a copy -shortest {videoFileName}";
    public static final String CONVERT_IMAGE_FORMAT = "ffmpeg -i {imageFileName} {newImageFileName}";
    public static final String CONVERT_VIDEO_FORMAT = "ffmpeg -i {inputVideoFileName} -vf pad=width=ceil(iw/2)*2:height=ceil(ih/2)*2 -c:a copy {outputVideoFileName}";
    public static final String UPSCALE_VIDEO_TO_1080P = "ffmpeg -i {inputVideoFileName} -vf scale=1920:1080 -c:a copy {outputVideoFileName}";
    public static final String UPSCALE_VIDEO_TO_1080P_MAINTAIN_ASPECT_RATIO = "time ffmpeg -i mage.mp4 -vf scale=1920:1080:flags=lanczos -c:a copy magelanczos.mp4";

    private CommandLineService commandLineService;
    private FileService fileService;
    private TimeService timeService;

    @Autowired
    public FfmpegService(CommandLineService commandLineService, FileService fileService, TimeService timeService) {
        this.commandLineService = commandLineService;
        this.fileService = fileService;
        this.timeService = timeService;
    }
    
    public VideoData upscaleVideoTo1080pMaintainAspectRatio(
        String inputVideoFileName,
        String outputVideoFileName) throws IOException, InterruptedException {
        Long start = timeService.getCurrentTimestamp();
        // Command to run FFmpeg to upscale video to 1080p
        String inputFile = inputVideoFileName;
        String outputFile = outputVideoFileName;
        log.info("inputFile = " + inputFile);
        log.info("outputFile = " + outputFile);
        String[] upscaleCommandArray = {
            "time", "ffmpeg",
            "-i", "{inputVideoFileName}",
            "-vf", "scale=1920:1080:flags=lanczos",
            "-c:a", "copy",
            "{outputVideoFileName}"
        };
        upscaleCommandArray[3] = inputVideoFileName;
        upscaleCommandArray[upscaleCommandArray.length
            - 1] = outputVideoFileName;

        log.info("running command: " + Arrays.asList((upscaleCommandArray))
            .toString());
        String result = commandLineService.executeCommandArray(
            upscaleCommandArray);
        log.info("result: " + result);
        byte[] outputVideoFileBytes = fileService.readFile(outputFile);
        VideoData videoData = VideoData.builder()
            .videoBytes(outputVideoFileBytes)
            .videoFileType("mp4")
            .build();
        Long end = timeService.getCurrentTimestamp();
        Double diff = (end.doubleValue() - start.doubleValue())
            / (ONE_THOUSAND.doubleValue());
        log.info("Time taken to upscale video: " + diff + " seconds");
        return videoData;
    }

    public VideoData changeVideoFormatArray(
        String inputVideoFileName,
        String outputVideoFileName) throws IOException, InterruptedException {
        Long start = timeService.getCurrentTimestamp();
        log.info("changeVideoFormatArray");
        String[] command = {
            "ffmpeg",
            "-i", "{inputVideo}", // Assuming the input file is named 'fVideo.mp4'
            "-vf", "crop=trunc(iw/2)*2:trunc(ih/2)*2",
            "-c:a", "copy", // This copies the audio stream without re-encoding
            "-pix_fmt", "yuv420p", // This sets the pixel format to 'yuv420p'
            "{outputVideo}" // Assuming the output file is named 'fVideo_out.mp4'
        };

        command[2] = inputVideoFileName;
        command[command.length - 1] = outputVideoFileName;
        log.info("running command: ");
        for (String commandToken : command) {
            log.info(commandToken);
        }
        String result = commandLineService.executeCommandArray(command);
        log.info("result: " + result);
        byte[] outputVideoFileBytes = fileService.readFile(outputVideoFileName);
        VideoData videoData = VideoData.builder()
            .videoBytes(outputVideoFileBytes)
            .videoFileType("mp4")
            .build();
        Long end = timeService.getCurrentTimestamp();
        Double diff = (end.doubleValue() - start.doubleValue())/(ONE_THOUSAND.doubleValue());
        log.info("Time taken to change video format: " + diff + " seconds");
        return videoData;
    }


    public VideoData changeVideoFormat(
        String inputVideoFileName,
        String outputVideoFileName) throws IOException, InterruptedException {
        log.info("change video format");
        String commandLine = CONVERT_VIDEO_FORMAT;
        commandLine = commandLine.replace("{inputVideoFileName}", inputVideoFileName);
        commandLine = commandLine.replace("{outputVideoFileName}", outputVideoFileName);
        log.info("change video format command: " + commandLine);
        String result = commandLineService.executeCommand(commandLine);
        log.info("command line result: " + result);
        byte[] outputVideoFileBytes = fileService.readFile(outputVideoFileName);
        String outputVideoFileType = outputVideoFileName.substring(outputVideoFileName.lastIndexOf(".") + 1);
        VideoData videoData = VideoData.builder()
            .videoBytes(outputVideoFileBytes)
            .videoFileType(outputVideoFileType)
            .build();
        log.info("updated format completed");
        return videoData;
    }

    public ImageData changeImageFormat(
        String imageFileName,
        String newImageFileName) throws IOException, InterruptedException {
        log.info("change image format");
        Long start = timeService.getCurrentTimestamp();
        String commandLine = CONVERT_IMAGE_FORMAT;
        commandLine = commandLine.replace("{imageFileName}", imageFileName);
        commandLine = commandLine.replace("{newImageFileName}", newImageFileName);
        log.info("change image format command: " + commandLine);
        String result = commandLineService.executeCommand(commandLine);
        log.info("command line result: " + result);
        byte[] newImageFileBytes = fileService.readFile(newImageFileName);
        String newImageFileType = newImageFileName.substring(newImageFileName.lastIndexOf(".") + 1);
        ImageData imageData = ImageData.builder()
            .imageBytes(newImageFileBytes)
            .imageFileType(newImageFileType)
            .build();
        log.info("updated format completed");
        Long end = timeService.getCurrentTimestamp();
        Double diff = (end.doubleValue() - start.doubleValue())/(ONE_THOUSAND.doubleValue());
        log.info("Time taken to change image format: " + diff + " seconds");
        return imageData;
    }

    public VideoData ffmpegCombineAudioAndImageIntoVideo(
        String imageFileName,
        String audioFileName,
        String videoFileName) throws IOException, InterruptedException {
        Long start = timeService.getCurrentTimestamp();
        log.info("ffmpeg combine audio and video");
        String commandLine = COMBINE_AUDIO_VIDEO_COMMAND;
        commandLine = commandLine.replace("{audioFileName}", audioFileName);
        commandLine = commandLine.replace("{imageFileName}", imageFileName);
        commandLine = commandLine.replace("{videoFileName}", videoFileName);
        log.info("commandLine = " + commandLine);
        String result = commandLineService.executeCommand(commandLine);
        log.info("command line result: " + result);
        byte[] videoFileBytes = fileService.readFile(videoFileName);
        String videoFileType = videoFileName.substring(videoFileName.lastIndexOf(".") + 1);
        VideoData videoData = VideoData.builder()
            .videoBytes(videoFileBytes)
            .videoFileType(videoFileType)
            .build();
        Long end = timeService.getCurrentTimestamp();
        Double diff = (end.doubleValue() - start.doubleValue())/(ONE_THOUSAND.doubleValue());
        log.info("Time taken to combine audio and video: " + diff + " seconds");
        return videoData;
    }
}
