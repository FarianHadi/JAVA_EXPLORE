package com;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class APIRequest implements Runnable{

    private Map<String, String> dataMap;
    private String token;

    public APIRequest(Map<String, String> dataMap, String token){
        this.dataMap = dataMap;
        this.token = token;
    }
    @Override
    public void run() {
        getFileConntent();
    }

    private void getFileConntent() {
        Contact contact = new Contact();
        Address address = new Address();
        contact.setTitleID(dataMap.get("1"));
        contact.setFirstName(dataMap.get("2"));
        contact.setLastName(dataMap.get("3"));
        contact.setKnownAs(dataMap.get("4"));
        address.setAddressLine1(dataMap.get("5"));
        address.setAddressLine2(dataMap.get("6"));
        address.setPostCode(dataMap.get("7"));
        address.setCity(dataMap.get("8"));
        address.setStateCode( dataMap.get("9"));
        address.setCountryCode(dataMap.get("10"));
        contact.setAddress(address);
        contact.setOfficePhone(dataMap.get("11"));
        contact.setFax(dataMap.get("12"));
        contact.setMobilePhone(dataMap.get("13"));
        contact.setPager(dataMap.get("14"));
        contact.setEmail(dataMap.get("15"));
        contact.setUserCategoryGUID(dataMap.get("16"));
        contact.setProviderNo(dataMap.get("17"));
        contact.setComment(dataMap.get("18"));

        ObjectMapper mapper = new ObjectMapper();
        String jsonResult = null;
        try {
            String jsonResult = mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(contact);
            System.out.println(jsonResult);
            createRequest(jsonResult);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void createRequest(String data) {
        try {
            URL url = new URL("http://localhost:8080/mrapp/rest/secured/contact/healthprofessional");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Authorization", token);
            // Send post request
            con.setDoOutput(true);
            DataOutputStream wr = null;
            try {
                wr = new DataOutputStream(con.getOutputStream());
                wr.writeBytes(data);
                wr.flush();
                wr.close();
            } catch (IOException exception) {
                throw exception;
            } finally {
                closeQuietly(wr);
            }


            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            con.disconnect();
        } catch (Exception e) {

        }

    }

    private void closeQuietly(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException ex) {

        }
    }
}
