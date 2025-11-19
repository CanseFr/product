
Migrer test vers h2
@OneToMany(cascade = CascadeType.ALL, mappedBy = "category")
JPQL vs  Implementation de l'interface pour la recherche auto selon les attributs de la class
Impl interface avec option comme trie et autre

Creation de methode dans l'interface qui etend de JPA pour beneficier de la detection de JPA vs
Creation de methode dans le Service directement, (methode perso, ...)

Controller requestApi en lien avec server.servlet.context-path=/product de application.properties


Etudier  @RepositoryRestResource(path="rest")
aves son pom.xml

http://localhost:8080/product/rest/search permet de recuperer les url/ctrl generé depuis les methode du repository de facon automatique 


Regarder de plus pret RepositoryRestConfiguration

Etudier projection



@Transicationnal sur la modofication do'bject et cas d'erreur, voir userServiceImpl

@PreAuthorize("hasAuthority('ADMIN')")
Sur controlelr comme sur service impl ...

Etudier la securité


public class EmailAlreadyExistException extends RuntimeException{
@ControllerAdvice
@ExceptionHandler(EmailAlreadyExistException.class)

@Projection(name = "nameProduct", types = Product.class)
public interface ProductProjection {
public String getNameProduct();
}


Model Mapper