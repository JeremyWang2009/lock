import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

/**
 * Created by shanguang.wang on 2019-04-02
 */
public class PipedWriterReaderTest {

    public static void main(String[] args) {
        PipedWriterReaderTest pipedWriterReaderTest = new PipedWriterReaderTest();
        pipedWriterReaderTest.testPipedWriterReader();
    }

    public void testPipedWriterReader() {
        PipedWriter pipedWriter = null;
        PipedReader pipedReader = null;

        try {
            pipedReader = new PipedReader();
            pipedWriter = new PipedWriter();
            pipedWriter.connect(pipedReader);
            new Thread(new WriteRunnable(pipedWriter)).start();
            new Thread(new ReadRunnable(pipedReader)).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class WriteRunnable implements Runnable {

        private PipedWriter pipedWriter = null;

        public WriteRunnable(PipedWriter pipedWriter) {
            this.pipedWriter = pipedWriter;
        }

        @Override
        public void run() {
            if (pipedWriter == null) {
                return;
            }
            try {
                pipedWriter.write("hello");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    static class ReadRunnable implements Runnable {

        private PipedReader pipedReader = null;

        public ReadRunnable(PipedReader pipedReader) {
            this.pipedReader = pipedReader;
        }

        @Override
        public void run() {
            if (pipedReader == null) {
                return;
            }
            try {
                if (pipedReader.ready()) {
                    char[] cbuf = new char[1024];
                    int len = 0;
                    if ((len = pipedReader.read(cbuf))!= -1) {
                        System.out.println(new String(cbuf, 0, len));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
