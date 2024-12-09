package ticketmachine;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class TicketMachineTest {
	private static final int PRICE = 50; // Une constante

	private TicketMachine machine; // l'objet à tester

	@BeforeEach
	public void setUp() {
		machine = new TicketMachine(PRICE); // On initialise l'objet à tester
	}

	@Test
	// On vérifie que le prix affiché correspond au paramètre passé lors de
	// l'initialisation
	// S1 : le prix affiché correspond à l’initialisation.
	void priceIsCorrectlyInitialized() {
		// Paramètres : valeur attendue, valeur effective, message si erreur
		assertEquals(PRICE, machine.getPrice(), "Initialisation incorrecte du prix");
	}

	@Test
	// S2 : la balance change quand on insère de l’argent
	void insertMoneyChangesBalance() {
		// GIVEN : une machine vierge (initialisée dans @BeforeEach)
		// WHEN On insère de l'argent
		machine.insertMoney(10);
		machine.insertMoney(20);
		// THEN La balance est mise à jour, les montants sont correctement additionnés
		assertEquals(10 + 20, machine.getBalance(), "La balance n'est pas correctement mise à jour");
	}

	@Test
	// S3 : On n'imprime pas le ticket si le montant inséré est insuffisant
	void notPrintIfNotEnoughMoney() {
		// GIVEN : On initialise une machine vierge ( dans @BeforeEach )
		// WHEN On ajoute de l'argent mais pas assez pour print un ticket
		machine.insertMoney(10);
		// THEN Le ticket ne doit pas s'imprimer car il n'y a pas assez d'argent dans la machine
		assertFalse(machine.printTicket(), "Le ticket s'imprime même si il n'y a pas assez d'argent");
	}

	@Test
	// S4 : On imprime le ticket si le montant inséré est suffisant
	void printTicketWhenEnoughMoney () {
		// GIVEN : On initialise une machine vierge ( dans @Before Each )
		// WHEN on ajoute assez d'argent pour print un ticket
		machine.insertMoney(PRICE);
		// THEN Le ticket s'imprime correctement
		assertTrue(machine.printTicket(), "Le ticket ne s'imprime pas même si il y a assez d'argent");

	}

	@Test
	// S5 : Quand on imprime un ticket la balance est décrémentée du prix du ticket
	void moneyIsUsed () {
		// GIVEN : On initialise une machine vierge ( dans @Before Each ) et on ajout de l'argent
		machine.insertMoney(PRICE);
		// WHEN on print un ticket
		machine.printTicket();
		// THEN La balance est décrémentée du prix du ticket
		assertEquals(50-PRICE,machine.getBalance(), "La balance ne s'est pas correctement décrémentée");
	}

	@Test
		// S6 : le montant collecté est mis à jour quand on imprime un ticket (pas avant)
	void totalIsUpdated () {
		// GIVEN : On initialise une machine vierge ( dans @Before Each ) et on ajout de l'argent
		machine.insertMoney(PRICE);
		// WHEN on print un ticket
		machine.printTicket();
		// THEN Le montant collecté est mis à jour
		assertEquals(PRICE,machine.getTotal(), "Le total est bien mis à jour");
	}

	@Test
	// S7 :refund() rend correctement la monnaie
	void refundGiveMoneyBack () {
		// GIVEN : on initialise une machine vierge ( dans @Before Each ) et on ajout de l'argent
		machine.insertMoney(PRICE);
		// WHEN on utilise la méthode refund ()
		// THEN la méthode rend la somme correcte
		assertEquals(PRICE,machine.refund(), "La somme récupérée n'est pas la bonne");
	}

	@Test
		// S8 :refund() rend correctement la monnaie
	void refundMakeBalanceTo0 () {
		// GIVEN : on initialise une machine vierge ( dans @Before Each ) et on ajout de l'argent
		machine.insertMoney(PRICE);
		// WHEN on utilise la méthode refund ()
		machine.refund();
		// THEN la méthode met la balance à 0
		assertEquals(0,machine.getBalance(), "La balance n'est pas vidée");
	}

	@Test
	// S9 : On ne peut pas insérer un moment négatif
	void noNegativeAmounts() {
	try {
		machine.insertMoney(-10);
		fail();
	} catch (IllegalArgumentException e){}

	}

	@Test
		// S10 : On ne peut pas créer de machine qui délivre des tickets dont le prix est négatif
	void noNegativePrice() {
		try {
			machine = new TicketMachine(-10);
			fail();
		} catch (IllegalArgumentException e){}
	}
}
