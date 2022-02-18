package com.hz.dao;

import com.common.entity.Document;

import java.util.List;

public interface DocDao {
    int addDoc(Document document);
    int deleteDoc(String docId);
    int updateDoc(Document document);
    Document getDoc(String docId);
    List<Document> getDocs(String userId);
    List<Document> getDocsPage(String userId);
}
