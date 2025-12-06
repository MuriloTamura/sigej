package br.ifce.sigej.controller;

import br.ifce.sigej.dao.PessoaDAO;
import br.ifce.sigej.model.Pessoa;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pessoas")
@CrossOrigin(origins = "*") // permite chamadas do front HTML
public class PessoaController {

    private final PessoaDAO dao = new PessoaDAO();

    @GetMapping
    public List<Pessoa> listar() {
        return dao.listar();
    }

    @PostMapping
    public String inserir(@RequestBody Pessoa pessoa) {
        dao.inserir(pessoa);
        return "Pessoa inserida com sucesso!";
    }

    @PutMapping("/{id}")
    public String atualizar(@PathVariable int id, @RequestBody Pessoa pessoa) {
        pessoa.setId(id);
        dao.atualizar(pessoa);
        return "Pessoa atualizada!";
    }

    @DeleteMapping("/{id}")
    public String deletar(@PathVariable int id) {
        dao.deletar(id);
        return "Pessoa deletada!";
    }
}
