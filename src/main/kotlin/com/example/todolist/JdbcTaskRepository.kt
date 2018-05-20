package com.example.todolist

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository

@Repository
class JdbcTaskRepository(private val jdbcTemplate: JdbcTemplate) : TaskRepository {

    private val rowMapper = RowMapper<Task> { rs, _ ->
        Task(rs.getLong("id"), rs.getString("content"), rs.getBoolean("done"), rs.getBoolean("del"))
    }

    override fun create(content: String): Task {
        jdbcTemplate.update("INSERT INTO task(content) VALUES(?)", content)
        val id = jdbcTemplate.queryForObject("SELECT last_insert_id()", Long::class.java)
        return Task(id, content, false, false)
    }

    override fun delete(id: String) {
        jdbcTemplate.update("UPDATE task SET del = ? WHERE id = ?", true, id)
    }

    override fun update(task: Task) {
        jdbcTemplate.update("UPDATE task SET content = ?,done = ? WHERE id = ?", task.content, task.done, task.id)
    }

    override fun findAll(): List<Task> =
            jdbcTemplate.query("SELECT id,content,done,del FROM task where del = FALSE", rowMapper)

    override fun findById(id: Long): Task? =
            jdbcTemplate.query("SELECT id,content,done,del FROM task WHERE id = ? and del = FALSE", rowMapper, id).firstOrNull()
}