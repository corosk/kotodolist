package com.example.todolist

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("tasks")
class TaskController(private val taskRepository: TaskRepository) {

    @GetMapping("")
    fun index(model: Model): String {
        val tasks = taskRepository.findAll()
        model.addAttribute("tasks", tasks)
        return "tasks/index"
    }

    @GetMapping("archive")
    fun archive(model: Model): String {
        val tasks = taskRepository.findAllForDelete()
        model.addAttribute("tasks", tasks)
        return "tasks/archive"
    }

    @GetMapping("vue")
    fun vue(): String {
        return "tasks/vue"
    }

    @GetMapping("new")
    fun new(form: TaskCreateForm): String {
        return "tasks/new"
    }

    @PostMapping("{id}/delete")
    fun delete(@PathVariable("id") id: Long): String {
        taskRepository.delete(id.toString())
        return "redirect:/tasks"
    }

    @PostMapping("")
    fun cretae(@Validated form: TaskCreateForm,
               bindingResult: BindingResult): String {
        if (bindingResult.hasErrors())
            return "tasks/new"

        val content = requireNotNull(form.content)
        taskRepository.create(content)
        return "redirect:/tasks"
    }

    @GetMapping("search")
    fun search(form: TaskSearchForm,
               model: Model): String {
        val keyword = requireNotNull(form.keyword)
        print("%$keyword%")
        val tasks = taskRepository.findMatchInKeyword("%$keyword%")
        for (value in tasks) {
            print(value.id)
            print("OK")// 1 2 3 4 5
        }
        model.addAttribute("tasks", tasks)
        return "tasks/search"
    }

    @GetMapping("{id}/edit")
    fun edit(@PathVariable("id") id: Long,
             form: TaskUpdateForm): String {
        val task = taskRepository.findById(id) ?: throw NotFoundException()
        form.content = task.content
        form.done = task.done
        return "tasks/edit"
    }

    @PatchMapping("{id}")
    fun update(@PathVariable("id") id: Long,
               @Validated form: TaskUpdateForm,
               bindingResult: BindingResult): String {
        if (bindingResult.hasErrors())
            return "tasks/edit"

        val task = taskRepository.findById(id) ?: throw NotFoundException()
        val newTask = task.copy(content = requireNotNull(form.content),
                done = form.done)
        taskRepository.update(newTask)
        return "redirect:/tasks"
    }

    @ExceptionHandler(NotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleNotFoundException(): String = "tasks/not_found"
}