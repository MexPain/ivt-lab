package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class GT4500Test {

  private GT4500 ship;
  private TorpedoStore mockPrimaryTorpedoStore;
  private TorpedoStore mockSecondaryTorpedoStore;

  @BeforeEach
  public void init(){
    mockPrimaryTorpedoStore = mock(TorpedoStore.class);
    mockSecondaryTorpedoStore = mock(TorpedoStore.class);
    this.ship = new GT4500(mockPrimaryTorpedoStore, mockSecondaryTorpedoStore);
  }

  @Test
  public void fireTorpedo_Single_Success(){
    // Arrange
    when(mockPrimaryTorpedoStore.fire(1)).thenReturn(true);

    // Act
    ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(mockPrimaryTorpedoStore, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_All_Success(){
    // Arrange
    when(mockPrimaryTorpedoStore.fire(1)).thenReturn(true);
    when(mockSecondaryTorpedoStore.fire(1)).thenReturn(true);

    // Act
    ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(mockPrimaryTorpedoStore, times(1)).fire(1);
    verify(mockSecondaryTorpedoStore, times(1)).fire(1);
  }

  @Test
  public void fireTorpedoSingleSecondary_Success(){
    //Arrange
    when(mockPrimaryTorpedoStore.fire(1)).thenReturn(true);
    when(mockSecondaryTorpedoStore.fire(1)).thenReturn(true);

    //Act
    boolean primaryResult = ship.fireTorpedo(FiringMode.SINGLE);
    boolean secondaryResult = ship.fireTorpedo(FiringMode.SINGLE);

    //Assert
    assertEquals(true, primaryResult);
    assertEquals(true, secondaryResult);
    verify(mockPrimaryTorpedoStore, times(1)).fire(1);
    verify(mockSecondaryTorpedoStore, times(1)).fire(1);
  }

  @Test
  public void fireTorpedoSingleBothEmpty_Failure(){
    //Arrange
    when(mockPrimaryTorpedoStore.isEmpty()).thenReturn(true);
    when(mockSecondaryTorpedoStore.isEmpty()).thenReturn(true);

    //Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    //Assert
    assertEquals(false, result);
  }

  @Test
  public void fireTorpedoSinglePrimaryEmpty_Success(){
    //Arrange
    when(mockPrimaryTorpedoStore.isEmpty()).thenReturn(true);
    when(mockSecondaryTorpedoStore.fire(1)).thenReturn(true);

    //Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    //Assert
    assertEquals(true, result);
    verify(mockPrimaryTorpedoStore, times(0)).fire(1);
    verify(mockSecondaryTorpedoStore, times(1)).fire(1);
  }

  @Test
  public void fireTorpedoAllBothEmpty_Failure(){
    //Arrange
    when(mockPrimaryTorpedoStore.isEmpty()).thenReturn(true);
    when(mockSecondaryTorpedoStore.isEmpty()).thenReturn(true);

    //Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    //Assert
    assertEquals(false, result);
  }

  @Test
  public void fireTorpedoAllSecondaryEmpty_Failure(){
    //Arrange
    when(mockSecondaryTorpedoStore.isEmpty()).thenReturn(true);

    //Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    //Assert
    assertEquals(false, result);
  }

  @Test
  public void fireTorpedoSingleSecondaryEmpty_Success(){
    //Arrange
    when(mockSecondaryTorpedoStore.isEmpty()).thenReturn(true);
    when(mockPrimaryTorpedoStore.fire(1)).thenReturn(true);

    //Act
    boolean result1 = ship.fireTorpedo(FiringMode.SINGLE);
    boolean result2 = ship.fireTorpedo(FiringMode.SINGLE);

    //Assert
    assertEquals(true, result1);
    assertEquals(true, result2);
    verify(mockPrimaryTorpedoStore, times(2)).fire(1);
    verify(mockSecondaryTorpedoStore, times(0)).fire(1);
  }

  @Test
  public void fireTorpedoAll_Success(){
    //Arrange
    when(mockPrimaryTorpedoStore.fire(1)).thenReturn(true);
    when(mockSecondaryTorpedoStore.fire(1)).thenReturn(true);

    //Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    //Assert
    assertEquals(true, result);
  }

}
