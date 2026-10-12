package com.example.firstproject.api;

import com.example.firstproject.dto.CoffeeDto;
import com.example.firstproject.entity.Coffee;
import com.example.firstproject.repository.CoffeeRepository;
import com.example.firstproject.service.CoffeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class CoffeeApiController {
    @Autowired
    private CoffeeService coffeeService;
    @Autowired
    private CoffeeRepository coffeeRepository;

    // GET
    @GetMapping("/api/coffees")
    public List<Coffee> index(){
        return coffeeService.index();
    }

    // GET
    @GetMapping("/api/coffees/{id}")
    public Coffee show(@PathVariable Long id){ // @PathVariable: url의 변수를 가져와서 매개변수로 전달
        return coffeeService.show(id);
    }

    // POST
    @PostMapping("/api/coffees")
    public ResponseEntity<Coffee> create(@RequestBody CoffeeDto dto){
        // create(): DB에 추가할 글 제목, 글 내용이 저장된 ArticleForm을 Article 엔티티로 변환해서 반환
        Coffee created = coffeeService.create(dto);

        return (created != null) ? ResponseEntity.status(HttpStatus.OK).body(created)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // PATCH
    @PatchMapping("/api/coffees/{id}")
    public ResponseEntity<Coffee> update(@PathVariable Long id, @RequestBody CoffeeDto dto){
        // Service 클래스에 글 수정 요청하여 수정된 Article 엔티티를 반환 받음
        Coffee updated = coffeeService.update(id, dto);

        // DB에 Article 엔티티를 수정 성공하면 성공 코드 반환
        // DB에 Article 엔티티를 수정 실패하면 실패 코드 반환
        return (updated != null) ? ResponseEntity.status(200).body(updated)
                : ResponseEntity.status(400).build();
    }
    // DELETE
    // 삭제할 글의 id를 전달 받아서 조회하여 삭제
    @DeleteMapping("/api/coffees/{id}")
    public ResponseEntity<Coffee> delete(@PathVariable Long id){
        // Service 클래스에 글 삭제 요청하여 삭제된 Article 엔티티를 반환 받음
        Coffee deleted = coffeeService.delete(id);

        return (deleted != null) ? ResponseEntity.status(HttpStatus.NO_CONTENT).build()
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // 트랜잭션
    // 작업을 수행할 때 모든 작업을 성공하지 못하고 중간에 실패한다면, 수행했던 모든 작업을 롤백함
    @PostMapping("/api/transaction-test2")
    public ResponseEntity<List<Coffee>> transactionTest(@RequestBody List<CoffeeDto> dtos){
        List<Coffee> createdList = coffeeService.createCoffee(dtos);

        return (createdList != null) ? ResponseEntity.status(HttpStatus.OK).body(createdList)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
