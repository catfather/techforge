package org.spring.dev.company.config;

import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import lombok.RequiredArgsConstructor;
import org.spring.dev.company.repository.member.MemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class KomoranConfig {

    private String USER_DIC = "user.dic";
    private final MemberRepository memberRepository;

    @Bean
    Komoran komoran(){
        userDic();
        Komoran komoran=new Komoran(DEFAULT_MODEL.LIGHT);
        komoran.setUserDic(USER_DIC);

        return komoran;
    }

    private void userDic() {

        Set<String> keys = new HashSet<>();

        try {
            File file=new File(USER_DIC);
            if(file.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String data = null;
                while ((data = br.readLine()) != null) {
                    if (data.startsWith("#"))
                        continue;
                    String[] str = data.split("\\t");
                    keys.add(str[0]);
                }
                br.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        memberRepository.findAll().forEach(e->{
            keys.add(e.getName());
            keys.add(e.getCompanyName());
        });

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(USER_DIC));
            keys.forEach(key -> {
                try {
                    bw.write(key + "\tNNP\n");
                    System.out.println(key);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            });

            bw.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }
}



