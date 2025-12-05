package com.kimleehan.bhproject.chat.service;

import com.kimleehan.bhproject.chat.dao.MessageRepository;
import com.kimleehan.bhproject.chat.model.MessageDTO;
import com.kimleehan.bhproject.user.parent.dao.ParentInfoRepository;
import com.kimleehan.bhproject.user.parent.dao.StudentInfoRepository;
import com.kimleehan.bhproject.user.parent.dto.*;
import com.kimleehan.bhproject.user.teacher.dao.ClassProgressRepository;

import com.kimleehan.bhproject.user.teacher.dao.TeacherInfoRepository;

import com.kimleehan.bhproject.chat.model.MessageEntity;
import com.kimleehan.bhproject.user.teacher.dto.TeacherInfoEntity;
import com.kimleehan.bhproject.user.teacher.service.ClassProgressService;

import lombok.RequiredArgsConstructor;


import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;


import java.util.List;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatService {

    private ModelAndView mav;

    //학부모 repository
    private final ParentInfoRepository prepo;

    // 학생/ 자녀 repository
    private final StudentInfoRepository srepo;

    // 학습진행 repository
    private final ClassProgressRepository cprepo;

    // 수업 진행 서비스
    private final ClassProgressService cpsvc;

    //선생님 repository
    private final TeacherInfoRepository trepo;

    // 메시지 repository
    private final MessageRepository messageRepository;

    // 학부모
    public ParentInfoEntity findParentById(String pId) {
        return prepo.findById(pId)
                .orElseThrow(() -> new RuntimeException("학부모 정보를 찾을 수 없습니다."));

    }

    // 자녀
    public List<StudentInfoDTO> findStudentByParentId(String pId) {
        List<StudentInfoEntity> student = srepo.findAllBypId_pId(pId);
        return student.stream()
                .map(StudentInfoDTO::toDTO)
                .collect(Collectors.toList());
    }

    // 선생님
    public TeacherInfoEntity findTeacherById(String tId) {
        return trepo.findById(tId)
                .orElseThrow(() -> new RuntimeException("선생님 정보를 찾을 수 없습니다."));

    }

    /**
     * 메시지 저장
     *
     * @param messageDTO 메시지 DTO
     */
    public void saveMessage(MessageDTO messageDTO) {
        MessageEntity messageEntity = MessageDTO.toEntity(messageDTO);
        messageRepository.save(messageEntity);
        System.out.println("[DB 저장] " + messageDTO.getSenderName() + " → " + messageDTO.getReceiverName() + " : " + messageDTO.getMessage());
    }


    // 전체 채팅 내역 불러오기
    public List<MessageDTO> getAllChatHistory(String username) {
        List<MessageEntity> messages = messageRepository.findBySenderNameOrReceiverNameOrderByDateAsc(username, username);
        return messages.stream()
                .map(MessageDTO::toDTO)
                .collect(Collectors.toList());
    }


    // 특정 사용자 간 채팅 내역 불러오기 허허허....
    public List<MessageDTO> getChatHistory(Long sId, String tId) {
        String sIdStr = String.valueOf(sId);
        return messageRepository.findBySenderNameAndReceiverNameOrReceiverNameAndSenderNameOrderByDateAsc(sIdStr, tId, sIdStr, tId)
                .stream()
                .map(MessageDTO::toDTO)
                .collect(Collectors.toList());
    }
}




