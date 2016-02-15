package com.gmail.mosoft521.jmtpdp.ch14hsha.example;

import com.gmail.mosoft521.jmtpdp.ch14hsha.AsyncTask;
import com.gmail.mosoft521.jmtpdp.util.Debug;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Future;

public class SampleAsyncTask {

    public static void main(String[] args) {
        XAsyncTask task = new XAsyncTask();
        List<Future<String>> results = new LinkedList<Future<String>>();

        try {
            results.add(task.doSomething("Half-sync/Half-async", 1));

            results.add(task.doSomething("Pattern", 2));

            for (Future<String> result : results) {
                Debug.info(result.get());
            }

            Thread.sleep(200);
        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    private static class XAsyncTask extends AsyncTask<String> {

        @Override
        protected String doInBackground(Object... params) {
            String message = (String) params[0];
            int sequence = (Integer) params[1];
            Debug.info("doInBackground:" + message);
            return "message " + sequence + ":" + message;
        }

        @Override
        protected void onPreExecute(Object... params) {
            String message = (String) params[0];
            int sequence = (Integer) params[1];
            Debug.info("onPreExecute:[" + sequence + "]" + message);
        }

        public Future<String> doSomething(String message, int sequence) {
            if (sequence < 0) {
                throw new IllegalArgumentException("Invalid sequence:" + sequence);
            }
            return this.dispatch(message, sequence);
        }
    }
}