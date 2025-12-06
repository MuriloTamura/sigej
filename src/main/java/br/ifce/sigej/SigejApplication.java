package br.ifce.sigej;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SigejApplication {

    public static void main(String[] args) {
        SpringApplication.run(SigejApplication.class, args);
        System.out.println("🚀 SIGEJ API iniciada com sucesso!");
    }
}
