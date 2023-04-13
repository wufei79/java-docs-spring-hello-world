package com.example.service.impl;

import com.example.dao.HistoryDAO;
import com.example.model.History;
import com.example.model.HistoryExample;
import com.example.service.HistoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryServiceImpl implements HistoryService {
    @Autowired
    HistoryDAO HistoryDAO;

    @Override
    public int insert(History history) throws Exception {
        // TODO Auto-generated method stub
        int result = 0;
        try {
            result = HistoryDAO.insert(history);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    @Override
    public List<History> selectByExample(HistoryExample example) throws Exception{
        // TODO Auto-generated method stub
        return HistoryDAO.selectByExample(example);
    }

    @Override
    public List<String> getConversationList() throws Exception{
        return HistoryDAO.getConversationList();
    }


    /*
    @Override
    public History selectById(int id) {
        HistoryExample HistoryExample = new HistoryExample();
        HistoryExample.createCriteria().andIdEqualTo(id);
        List<History> Historys =  HistoryDAO.selectByExample(HistoryExample);
        HistoryDAO.insertSelective(null);
        if(Historys!=null&&Historys.size()>0){
           return Historys.get(0);
        }
        return null;
    }
    */
//
//    @Override
//    public List<History> selectList(History History) {
//        return HistoryDAO.selectList(History);
//    }


}
