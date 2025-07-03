Feature: Gestión de bonos financieros

  Scenario: Crear un nuevo bono y verificar su existencia
    Given un bono con nombre "Bono de prueba" y valor nominal de 1000.0
    Then el bono debe estar registrado en el sistema

