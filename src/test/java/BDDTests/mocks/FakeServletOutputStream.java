package BDDTests.mocks;

import javax.servlet.ServletOutputStream;
import java.io.IOException;

public class FakeServletOutputStream extends ServletOutputStream {

    private StringBuilder builder;

    public FakeServletOutputStream() {
        this.builder = new StringBuilder();
    }

    @Override
    public void write(int b) throws IOException {
        this.builder.append((char)b);

    }

    public StringBuilder getStringBuilder() {
        return this.builder;
    }
}
