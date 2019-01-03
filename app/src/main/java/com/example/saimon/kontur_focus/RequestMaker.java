package com.example.saimon.kontur_focus;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

class RequestMaker {

    // Слушатель, при помощи которого отправим обратный вызов о готовности страницы
    private OnRequestListener listener;

    // В конструкторе примем слушателя, а в дальнейшем передадим его асинхронной задаче
    RequestMaker(OnRequestListener onRequestListener) {
        listener = onRequestListener;
    }

    // Сделать запрос
    void make(String uri) {
        // Создаем объект асинхронной задачи (передаем ей слушатель)
        Requester requester = new Requester(listener);
        // Запускаем асинхронную задачу
        requester.execute(uri);
    }

    // Интерфейс слушателя с методами обратного вызова
    public interface OnRequestListener {
        void onStatusProgress(String updateProgress);   // Вызов для обновления прогресса
        void onComplete(String result);                 // Вызов при завершении обработки
    }

    // AsyncTask - это обертка для выполнения потока в фоне
    // Начальные и конечные методы работают в потоке UI, а основной метод расчета работает в фоне
    private static class Requester extends AsyncTask<String, String, String> {
        private OnRequestListener listener;

        Requester(OnRequestListener listener) {
            this.listener = listener;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //... Сделайте подготовку перед началом работы фонового потока
        }

        // Обновление прогресса, работает в основном потоке UI
        @Override
        protected void onProgressUpdate(String... strings) {
            listener.onStatusProgress(strings[0]);
        }

        // Выполнить таск в фоновом потоке
        @Override
        protected String doInBackground(String... strings) {
            return getResourceUri(strings[0]);
        }

        // Выдать результат (работает в основном потоке UI)
        @Override
        protected void onPostExecute(String content) {
            listener.onComplete(content);
        }

        // Обработка загрузки страницы
        private String getResourceUri(String uri) {
            HttpURLConnection urlConnection = null;
            try {
                URL url = new URL(uri); // Указать адрес URI
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET"); // Установка метода получения данных - GET
                urlConnection.setReadTimeout(10000); // Установка таймаута - 10 000 миллисекунд
                publishProgress("Подготовка данных"); // Обновим прогресс
                urlConnection.connect();              // Соединиться
                publishProgress("Соединение");        // Обновим прогресс
                BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));   // Читаем  данные в поток ввода/вывода
                StringBuilder buf = new StringBuilder(); // Здесь будем формировать результат
                publishProgress("Получение данных");  // Обновим прогресс
                // Обработка выходных данных in
                String line = null;
                int numLine = 0; // Эта переменная нужна лишь для показа прогресса
                // Читаем все строки из полученных выходных данных
                while ((line = in.readLine()) != null) {
                    numLine++;
                    // Обновим прогресс
                    publishProgress(String.format("Строка %d", numLine));
                    buf.append(line);   // Добавим еще одну строку в результат
                    // Это перевод каретки
                    buf.append(System.getProperty("line.separator"));
                }
                return buf.toString();

            } catch (Exception e) {
                Log.e("WebBrowser", e.getMessage(), e);
                // Обновим прогресс
                publishProgress("Ошибка");
                return "Ошибка получения URL";
            } finally {
                // Разъединиться
                if (urlConnection != null) urlConnection.disconnect();
            }
        }
    }
}
