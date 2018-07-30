package com.hzyice.user.repository;

import com.hzyice.user.dataObject.UserInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.UUID;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserInfoRepositoryTest {

    @Autowired
    private UserInfoRepository repository;



    @Test
    public void findByUsernameAndPasswordAndOpenid() throws Exception {
//        UserInfo userInfo = repository.findByUsernameAndPasswordAndOpenid("zs", "123456", "666666");
//        System.out.println(userInfo);

    }

    @Test
    public void saveUserInfo() {
        // 生成32随机数
        String id = UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");
        Date date = new Date();
        UserInfo userInfo = new UserInfo(id, "xm", "123789", "888888", 2, date, date);
        UserInfo save = repository.save(userInfo);
        System.out.println(save);
    }

}