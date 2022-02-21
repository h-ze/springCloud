package com.hz.service.impl;

import com.common.entity.Document;
import com.common.entity.PageRequest;
import com.common.entity.PageResult;
import com.common.utils.PageUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hz.dao.DocDao;
import com.hz.service.DocService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("docService")
@Transactional
public class DocServiceImpl implements DocService {

    private static final Logger logger = LoggerFactory.getLogger(DocServiceImpl.class);

    @Autowired
    private DocDao docDao;

    @Override
    public int createDocument(Document document) {
        return docDao.addDoc(document);
    }

    @Override
    public int deleteDocument(String docId) {
        return docDao.deleteDoc(docId);
    }

    @Override
    public int updateDocument(Document document) {
        return docDao.updateDoc(document);
    }

    @Override
    public Document getDocument(String docId) {
        return docDao.getDoc(docId);
    }

    @Override
    public List<Document> getDocumentList(String userId) {
        return docDao.getDocs(userId);
    }

    @Override
    public PageResult getDocsPage(PageRequest pageRequest,String userId) {
        return PageUtils.getPageResult(getPageInfo(pageRequest,userId));
    }

    /**
     * 调用分页插件完成分页
     * @return
     */
    private PageInfo<Document> getPageInfo(PageRequest pageRequest,String userId) {
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        List<Document> docsPage = docDao.getDocsPage(userId);
        return new PageInfo<>(docsPage);
    }
}
