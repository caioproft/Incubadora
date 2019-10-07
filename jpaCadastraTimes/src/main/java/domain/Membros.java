package domain;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "membros")
public class Membros {

    // Atributos da classe Membros e referências na tabela criada no postgres
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "time_id", nullable = false) // realiza o join entre o time_id e a classe time
    private Times time;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;


    // Construtores
    public Membros(){
    }
    public Membros(final String nome, final Times time){
        this.nome = nome;
        this.time = time;
    }

    // Getters e Setters
    public Long getId(){
        return id;
    }
    public void setId(final Long id){
        this.id = id;
    }
    public String getNome(){
        return nome;
    }
    public void setNome(final String nome){
        this.nome = nome;
    }
    public Times getTime(){
        return time;
    }
    public void setTime(final Times time){
        this.time = time;
    }
    public LocalDateTime getCreatedAt(){
        return createdAt;
    }
    public void setCreatedAt(final LocalDateTime createdAt){
        this.createdAt = createdAt;
    }
    public LocalDateTime getUpdatedAt(){
        return updatedAt;
    }
    public void setUpdatedAt(final LocalDateTime updatedAt){
        this.updatedAt = updatedAt;
    }

    // Método toString para retorno dos dados obtidos
    public String toString() {
        return "id: "
                + id
                + " nome: "
                + nome
                + "time: "
                + time
                + " createdAt: "
                + createdAt
                + " updatedAt: "
                + updatedAt;
    }

}
