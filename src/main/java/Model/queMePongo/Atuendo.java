package Model.queMePongo;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name="atuendo")
public class Atuendo {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @ManyToMany
    @JoinTable(
            name="atuendo_prenda_basico",
            joinColumns={@JoinColumn(name="id_atuendo")},
            inverseJoinColumns={@JoinColumn(name="id_prenda")})
    private List<Prenda> prendasbasicas = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name="atuendo_prenda_abrigo",
            joinColumns={@JoinColumn(name="id_atuendo")},
            inverseJoinColumns={@JoinColumn(name="id_prenda")})
    private List<Prenda> prendasDeAbrigo = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name="atuendo_prenda_accesorios",
            joinColumns={@JoinColumn(name="id_atuendo")},
            inverseJoinColumns={@JoinColumn(name="id_prenda")})
    private List<Prenda> accesorios = new ArrayList<>();

    public void reservarAtuendo() {
        prendasbasicas.forEach(Prenda::reservarPrenda);
        prendasDeAbrigo.forEach(Prenda::reservarPrenda);
    }

    public void liberarAtuendo() {
        prendasbasicas.forEach(Prenda::liberarPrenda);
        prendasDeAbrigo.forEach(Prenda::liberarPrenda);
    }

    public List<Prenda> getPrendas(){
        List<Prenda> prendas = new ArrayList<>();

        prendas.addAll(prendasbasicas);
        prendas.addAll(prendasDeAbrigo);

        return prendas;
    }

    public String toString(){
        StringBuilder atuendoBuilder = new StringBuilder("\n\tAtuendo: " + "\n\t");

        atuendoBuilder.append("Prendas basicas:\n\t");
        prendasbasicas.forEach((p)-> atuendoBuilder.append(p.toString()).append(", "));
        if(!prendasDeAbrigo.isEmpty()){
            atuendoBuilder.append("\n\tPrendas de abrigos:\n\t");
            prendasDeAbrigo.forEach((p)-> atuendoBuilder.append(p.toString()).append(", "));
        }
        if(!accesorios.isEmpty()){
            atuendoBuilder.append("\n\tPrendas de accesorios:\n\t");
            accesorios.forEach((p)-> atuendoBuilder.append(p.toString()).append(", "));
        }
        String atuendo = atuendoBuilder.toString();
        return atuendo.substring(0, atuendo.length() - 2) + "\n";
    }
}
