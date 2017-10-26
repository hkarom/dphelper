

Requis : 
JRE 8 minimum , Distribution Linux / Materiels (Minimum requis pour compiler un programme Java )
IntellijIdea avec Maven installe .

Fichier : Patternshelper 
	
		-ressources :==> contient les programmes de test pour les Design pattern implemente .        
			-DataFiles 
				-abs_factory
				-decorator
				-filesForUnitsTests
				-singleton
				-visitor
			-DataFiles2:
		-src        :==> contient le code source de l'outil .  
			-dpHelper
				-annotations :==> contient les annotations pour les differents design pattern implemente .
					AbstractFactory.java
					Decorator.java
					Observer.java
					Singleton.java
					Visitor.java
				-exceptions  :==> contient les differentes exceptions qui sont geres .
					ElementKindExeption.java
					ModifierException.java
					NumberOfElementException.java
					TypeException.java
					VerifierException.java	
				-generators	:==> contient le code source des outils generateurs de code. 
				-tools		:==> contient les classes utilitaires de gestions des messages erreurs.
					AbstractFactoryError.java
					ErrorMessages.java
					ProcessorTools.java
					SingletonError.java
				-verifiers	:==> contient le code des verificateurs pour chaque design pattern ainsi que le dossier fluentapi
				
					-fluentapi :==>  contient les verificateurs sur les specifications .
  						AbstractCheck.java
						AbstractSpecification.java
						Checker.java
						ExistCheck.java
						IChecker.java
						IExistCheck.java
						IKindCheck.java
						ISpecification.java
						KindCheck.java
						Specification.java			
					AbstractFactoryVerifier.java	:==>
					DecoratorVerifier.java		:==>
					ObserverVerifier.java		:==>
					PatternVerifier.java       	:==>        
					SingletonVerifier.java		:==>
					SingleVerifier.java		:==>
					VisitorVerifier.java	 	:==>
				-visitors    :	:==> contient les visiteurs sur les differents types d'elements 
						AbsElementVisitor.java		:==>
						ElementAnnotationVisitor.java	:==>
						ElementInterfaceVisitor.java	:==>
						ElementKindVisitor.java		:==> 
						ElementModifierVisitor.java	:==>
						ElementNameVisitor.java		:==>
						ElementSuperClassVisitor.java	:==>
						ElementTypeVisitor.java 	:==> 
				PatternsProcessor.java	:==> Classe principale du verificateur . 		
		-target :==> Contient les dossiers et fichiers generes apres la compilation
			-classes
				-Datafiles
				-DataFiles2
				-dpHelper
				-META-INF
			-generated-sources
			-generated-test-sources
			-maven-archiver
			-maven-status
			-surefire-reports
			-test-classes
			PatternsHelper-1.0.jar		:==> Executable de l'outil qui est genere apres la compilation . 	     
		-test    
			-coverTests			:==> contient les tests de couvertures realises pour les design patterns implemente .
				SingletonTest.java	 
			-unitTests    			:==> contient les test unitaires pour les differents modules du projets .
				CompilerWithProcessorTest.java
				PatternsProcessorTest.java
				TestChecker.java
				TestSpecification.java
			
		launchCompiler.sh		
		pom.xml	:==> fichier de configuration du projet maven
		README
 
Lancer les quelques tests unitaires : mvn test
mvn -Dmaven.test.skip=true install

Compiler le projet (generer le fichier .jar) 
se placer dans le repertoire 
patternsHelper/
lancer la commande :==> mvn install -DskipTests

Faire une verification sur un fichier Java

lancer la commande avec les parametres qu'il faut :==> javac -cp chemin/../Extension.jar package/[nom_fichier].java

Faire une verification sur une hierarchie de classe 

lancer la commande avec les parametres qu'il faut :==>javac -cp chemin/../Extension.jar package/*.java


----------------------------Author-------------------------------- :
Hasssana Fikri---Talibe Balde---Bineta Diagne---Cheikh A B DIOP


Master Informatique Universite de Bordeaux 2016/2017

