package book.data.service.service.content;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CommandLineService {

    public String executeCommand(String commandLine) throws IOException, InterruptedException {
        String[] command = commandLine.split("\\s+");
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.redirectErrorStream(true);
        // Start the process
        Process process = processBuilder.start();

        // Read the output from the command
        StringBuilder output = new StringBuilder();
        BufferedReader reader = new BufferedReader(
            new InputStreamReader(process.getInputStream())
        );

        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");
        }

        // Wait for the process to terminate and check the exit value
        int exitCode = process.waitFor();
        if (exitCode != 0) {
            log.info("Error executing command, exit code " + exitCode + "\n" + output.toString());
            log.error("ERROR COMPLETING THE COMMAND: " + commandLine);
            throw new RuntimeException("CommandLine Error");
        }

        return output.toString();
    }

    public String executeCommandArray(String[] command) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.redirectErrorStream(true);
        // Start the process
        Process process = processBuilder.start();

        // Read the output from the command
        StringBuilder output = new StringBuilder();
        BufferedReader reader = new BufferedReader(
            new InputStreamReader(process.getInputStream())
        );

        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");
        }

        String commandLineString = String.join(" ", command);
        System.out.println("commandLineString: " + commandLineString);

        // Wait for the process to terminate and check the exit value
        int exitCode = process.waitFor();
        if (exitCode != 0) {
            log.info("Error executing command, exit code " + exitCode + "\n" + output.toString());
            log.error("ERROR COMPLETING THE COMMAND: " + commandLineString);
            log.info("error: " + output.toString());
            throw new RuntimeException("CommandLine Error");
        }

        return output.toString();
    }
}
