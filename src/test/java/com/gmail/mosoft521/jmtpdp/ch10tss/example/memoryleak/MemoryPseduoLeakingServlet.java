package com.gmail.mosoft521.jmtpdp.ch10tss.example.memoryleak;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicInteger;

public class MemoryPseduoLeakingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final static ThreadLocal<AtomicInteger> TL_COUNTER = new ThreadLocal<AtomicInteger>() {
        @Override
        protected AtomicInteger initialValue() {
            return new AtomicInteger();
        }

    };

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {


        PrintWriter pwr = resp.getWriter();
        pwr.write(TL_COUNTER.get().getAndIncrement());
        pwr.close();
    }
}