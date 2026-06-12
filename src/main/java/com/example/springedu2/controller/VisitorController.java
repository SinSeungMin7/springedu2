package com.example.springedu2.controller;

import com.example.springedu2.entity.Visitor;
import com.example.springedu2.repository.VisitorRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class VisitorController {

    // 1. @Autowired 이용 생성자 주입 대신 생성자 주입
    // @Autowired
    // private VisitorRepository visitorRepository;

    // 2. 생성자 주입 : 요즘 방식
    // private VisitorRepository visitorRepository;
    // public VisitorController(VisitorRepository visitorRepository) {
    //    this.visitorRepository = visitorRepository;
    // }

    // 3. 생성자 주입 다른 방법
    // @RequiredArgsConstructor 필수 : 단점 Lombok 을 꼭 써야한다
    private final VisitorRepository visitorRepository;

    // 방명록 조회
    @GetMapping("/vlist")
    public ModelAndView vlist() {
        List<Visitor> visitors = visitorRepository.findAll(); // 목록 조회
        return visitorView(visitors, null);
    }

    private ModelAndView visitorView(List<Visitor> visitors, String buttonText) {
        ModelAndView mv = new ModelAndView("visitorView");
        // mv.setViewName("visitorView"); // visitorView.html(model 을 사용) - thymeleaf 을 의미한다
        if( visitors.isEmpty() ){
            mv.addObject("msg", "조회된 결과가 없습니다");
        } else {
            mv.addObject("vList", visitors);
        }
        if ( buttonText != null ){
            mv.addObject("buttonText", buttonText);
        }
        return mv;
    }

    @GetMapping("/vsearch")
    public ModelAndView vsearch() {
        return null;
    }

    // @Valid : form 에서 넘어온 자료를 @Entity 에 있는
    // 설정(@Id, @NotBlank, @Coulumn(nullable=flase) )
    // 과 비교 해서 입력 data 를 검증한다
    @PostMapping("/vinsert")
    @Transactional
    public String vinsert(@Valid Visitor visitor,
            BindingResult bindingResult,
            Model model) {

        System.out.println("visitor:" + visitor);
        System.out.println("bindingResult:" +  bindingResult);
        if (bindingResult.hasErrors()){
            model.addAttribute("msg",
                    "이름과 내용을 모두 입력하세요");
            return "visitorView"; // visitorView.hteml
        }
        visitorRepository.save(visitor); // entity 객체를 사용해야한다
        return "redirect:/vlist";
    }
}
