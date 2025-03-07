# Paginación

- **El `Paginador`** se encarga de gestionar la paginación. Guarda la página actual, 
el tamaño de la página y el total de resultados.
- **El `PeliculaDAO`** se encarga de hacer las consultas a la base de datos.

🔹 **¿Cómo trabajan juntos?**
1. **El `Paginador` usa el `DAO`** para contar cuántas películas hay con la 
palabra clave (`contarPeliculasPorNombre`).
2. Cuando el usuario quiere ver una página, el `Paginador` llama al `DAO` para obtener 
los resultados de esa página (`obtenerPeliculasPorPagina`).
3. El `Paginador` controla la navegación (siguiente/anterior página) y calcula cuántas páginas hay en total.

Básicamente, el `Paginador` gestiona la lógica de paginación y el `DAO` se encarga de 
obtener los datos de la base de datos. 

En el **`main`**, la idea es:

1. **Crear el `Paginador` e inicializarlo**, pasándole el tamaño de página, la palabra 
clave de búsqueda y el `EntityManager`.
2. **Llamar a `getResultadosActuales()`** para obtener las películas de la primera página.
3. **Permitir la navegación** (siguiente/anterior página) mientras el usuario quiera seguir viendo resultados.
4. **Llamar a `next()` o `previous()`** según la opción del usuario y volver a mostrar los resultados.
5. **Finalizar el `Paginador` cuando ya no se necesite (`finished()`)**.

El `main` es el que interactúa con el usuario y usa el `Paginador` para obtener los datos 
de la BD sin preocuparse de cómo funciona internamente. 🚀

# Queries

## Query de Paginación en PeliculaDAO.java

```java
class PeliculaDAO {
    
    // Amósanse os datos da película especificados e o director da película
    public List<PeliculaPaginaDTO> obtenerPeliculasPorPagina(String palabraClave, int pagina, int tamañoPagina) {
        return em.createQuery(
                
                //Evitamos duplicados con Distinct
                // Creamos un objeto PeliculaPaginaDTO e inicializamolos cos campos que extraemos de Pelicula p
                // Como non hai un campo "Director" en Pelicula, simplemente o collemos de Personaxe.nome
                // e con COALESCE asegurámonos de que se per.nome é null colla o valor "Desconocido"
                        "SELECT DISTINCT NEW com.pepinho.ad.jpa.dto.PeliculaPaginaDTO(p.idPelicula, p.castelan, p.orixinal, p.anoFin, COALESCE(per.nome, 'Desconocido')) " +
                                // Sempre lle hai que poñer un alias "p" á tabla "Pelicula"
                                "FROM Pelicula p " +
                                // Relacionamos Pelicula con PeliculaPersonaxe a través do ID da Peli, ten que ser o mesmo
                                // Usamos LEFT JOIN para que colla todas as peliculas, teñan director ou non
                                // Con un JOIN solo colleria as peliculas con director
                                "LEFT JOIN PeliculaPersonaxe pp ON p.idPelicula = pp.pelicula.idPelicula " +
                                // Relacionamos PeliculaPersonaxe con Personaxe a través do ID de personaxe
                                // De novo, LEFT JOIN porque non tódalas películas teñen directores rexistrados na 
                                // tabla Personaxe
                                "LEFT JOIN Personaxe per ON pp.personaxe.idPersonaxe = per.idPersonaxe " +
                                // Relacionamos PeliculaPersonaxe con Ocupacion a través da propia ocupacion
                                // Con LEFT JOIN tamén para collelos todos
                                "LEFT JOIN Ocupacion o ON pp.ocupacion = o " +
                                // Filtramos por nombre, tódolos filtros deben aparecer no WHERE
                                // Convertimos o título en minúsculas e comparámolo co parámetro que establecemos nos
                                "WHERE LOWER(p.castelan) LIKE LOWER(:nombre) " +
                                // Filtramos o campo de Ocupacion.ocupacion para que solo colla aquel que sea DIRECTOR
                                "AND o.ocupacion = 'Director' " +  // Asegura que solo tome directores
                                "ORDER BY p.anoFin DESC",
                        PeliculaPaginaDTO.class)
                //Entre porcentaxes para que poida aparecer no medio de calquera outro caracter
                // Para buscar que teña a palabra solo habería que añadir espacios entre os %,
                // Tal como está, a nosa palabra pode estar no medio doutra: "enAMORado"
                .setParameter("nombre", "%" + palabraClave.toLowerCase() + "%")
                // Establécese o primeiro resultado que debe aparecer, sempre se empeza en pagina 0
                .setFirstResult(pagina * tamañoPagina)
                // Indícase cantos resultados deben aparecer na consulta
                .setMaxResults(tamañoPagina)
                // Executamos a consulta e obtemos a lista de resultados
                .getResultList();
    }
}
```

## Notas

- O ejercicio do examen de recu vámolo facer con herdanza, tres tablas, algo sencillo, no examen será parecido (?)
    - Vanse pedir duas estratexias (unha única tabla, todos os atributos na mesma tabla --> single table e as joined table; table-per-class non o vamos usar)
    - TypeDiscriminator cae no examen, tal vez
- É probable que caia algo de paginación
- Facer un DTO cae fijo

- En Spring caen duas formas de executar: con CommandLineRunner e un Componente