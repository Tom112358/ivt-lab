package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

public class GT4500Test {

  private GT4500 ship;
  private TorpedoStore ts1;
  private TorpedoStore ts2;

  @BeforeEach
  public void init() {
    ts1 = mock(TorpedoStore.class);
    ts2 = mock(TorpedoStore.class);
    this.ship = new GT4500(ts1, ts2);
  }

  @Test
  public void fireTorpedo_Single_Once_Success() {
    // Arrange
    when(ts1.fire(1)).thenReturn(true);
    when(ts2.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    verify(ts1, times(1)).fire(1);
    verify(ts2, times(0)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_Twice_Success() {
    // Arrange
    when(ts1.fire(1)).thenReturn(true);
    when(ts2.fire(1)).thenReturn(true);

    // Act
    boolean result1 = ship.fireTorpedo(FiringMode.SINGLE);
    boolean result2 = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result1);
    assertEquals(true, result2);
    verify(ts1, times(1)).fire(1);
    verify(ts2, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_Once_Primary_Empty_Success() {
    // Arrange
    when(ts1.fire(1)).thenReturn(false);
    when(ts1.isEmpty()).thenReturn(true);
    when(ts2.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    verify(ts1, times(1)).isEmpty();
    verify(ts1, times(0)).fire(1);
    verify(ts2, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_Twice_Secondary_Empty_Success() {
    // Arrange
    when(ts1.fire(1)).thenReturn(true);
    when(ts2.fire(1)).thenReturn(true);
    when(ts2.isEmpty()).thenReturn(true);

    // Act
    boolean result1 = ship.fireTorpedo(FiringMode.SINGLE);
    boolean result2 = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result1);
    assertEquals(true, result2);
    verify(ts1, times(2)).fire(1);
    verify(ts2, times(0)).fire(1);
    verify(ts2, times(1)).isEmpty();
  }

  @Test
  public void fireTorpedo_Single_Once_Both_Empty_Failure() {
    // Arrange
    when(ts1.fire(1)).thenReturn(false);
    when(ts1.isEmpty()).thenReturn(true);
    when(ts2.fire(1)).thenReturn(false);
    when(ts2.isEmpty()).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(false, result);
    verify(ts1, times(0)).fire(1);
    verify(ts1, times(1)).isEmpty();
    verify(ts2, times(0)).fire(1);
    verify(ts2, times(1)).isEmpty();
  }

  @Test
  public void fireTorpedo_All_Success() {
    // Arrange
    when(ts1.fire(1)).thenReturn(true);
    when(ts2.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
    verify(ts1, times(1)).fire(1);
    verify(ts2, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_All_Failure() {
    // Arrange
    when(ts1.fire(1)).thenReturn(true);
    when(ts1.isEmpty()).thenReturn(true);
    when(ts2.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(false, result);
    verify(ts1, times(0)).fire(1);
    verify(ts1, times(1)).isEmpty();
    verify(ts2, times(0)).fire(1);
    verify(ts2, times(0)).isEmpty();
  }

  @Test
  public void fireTorpedo_All_Failure2() {
    // Arrange
    when(ts1.fire(1)).thenReturn(true);
    when(ts2.fire(1)).thenReturn(true);
    when(ts2.isEmpty()).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(false, result);
    verify(ts1, times(0)).fire(1);
    verify(ts1, times(1)).isEmpty();
    verify(ts2, times(0)).fire(1);
    verify(ts2, times(1)).isEmpty();
  }

  @Test
  public void fireTorpedo_All_Primary_Fail_Failure() {
    // Arrange
    when(ts1.fire(1)).thenReturn(false);
    when(ts2.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(false, result);
    verify(ts1, times(1)).fire(1);
    verify(ts1, times(1)).isEmpty();
    verify(ts2, times(0)).fire(1);
    verify(ts2, times(1)).isEmpty();
  }

  @Test
  public void fireTorpedo_All_Secondary_Fail_Failure() {
    // Arrange
    when(ts1.fire(1)).thenReturn(true);
    when(ts2.fire(1)).thenReturn(false);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(false, result);
    verify(ts1, times(1)).fire(1);
    verify(ts1, times(1)).isEmpty();
    verify(ts2, times(1)).fire(1);
    verify(ts2, times(1)).isEmpty();
  }

  @Test
  public void fireTorpedo_Single_Twice_Last_Primary_Empty() {
    // Arrange
    when(ts1.fire(1)).thenReturn(true);
    when(ts2.isEmpty()).thenReturn(true);

    // Act
    boolean result1 = ship.fireTorpedo(FiringMode.SINGLE);

    when(ts1.isEmpty()).thenReturn(true);

    boolean result2 = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result1);
    assertEquals(false, result2);
    verify(ts1, times(1)).fire(1);
    verify(ts1, times(2)).isEmpty();
    verify(ts2, times(0)).fire(1);
    verify(ts2, times(1)).isEmpty();
  }
}
