import com.pi4j.io.gpio.*;
import com.pi4j.io.i2c.I2CFactory;
import com.pi4j.io.i2c.I2CDevice;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class SensorController {
    private static final int MOISTURE_PIN = 4;
    private static final String PH_SENSOR_ADDRESS = "0x48";
    private static final int PH_SENSOR_CHANNEL = 1;
    private static final int PH_SENSOR_REGISTER = 0x01;
    private static final double PH_FACTOR = 100.0;

    private final GpioController gpio = GpioFactory.getInstance();
    private final GpioPinDigitalInput moisturePin = gpio.provisionDigitalInputPin(RaspiPin.getPinByAddress(MOISTURE_PIN), PinPullResistance.PULL_DOWN);
    private final I2CDevice pHsensor;

    public SensorController() throws IOException, I2CFactory.UnsupportedBusNumberException {
        pHsensor = I2CFactory.getInstance(I2CBus.BUS_1).getDevice(Integer.decode(PH_SENSOR_ADDRESS));
    }

    @Scheduled(fixedDelay = 10000) // wait for 10 seconds
    public void readSensors() {
        // Read the moisture level from the sensor
        final int moistureLevel = moisturePin.getState().getValue();
        System.out.println("Moisture level: " + moistureLevel);

        // Read the pH value from the sensor
        try {
            final byte[] buffer = new byte[2];
            pHsensor.read(PH_SENSOR_REGISTER, buffer, 0, buffer.length);
            final double pHvalue = ((buffer[0] << 8) | (buffer[1] & 0xFF)) / PH_FACTOR;
            System.out.println("pH value: " + pHvalue);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Store the data in appropriate data structures or variables
        final LocalDateTime timestamp = LocalDateTime.now();
        final String timestampString = timestamp.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        System.out.println("Timestamp: " + timestampString);
        // Store the data as needed
    }
}
