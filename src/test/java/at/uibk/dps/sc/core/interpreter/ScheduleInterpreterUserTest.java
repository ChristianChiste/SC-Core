package at.uibk.dps.sc.core.interpreter;

import java.util.HashSet;
import java.util.Set;
import org.junit.Test;
import org.mockito.Mockito;
import at.uibk.dps.ee.core.enactable.EnactmentFunction;
import at.uibk.dps.ee.enactables.local.ConstantsLocal.LocalCalculations;
import at.uibk.dps.ee.enactables.local.LocalFunctionAbstract;
import at.uibk.dps.ee.enactables.local.calculation.FunctionFactoryLocal;
import at.uibk.dps.ee.enactables.serverless.FunctionFactoryServerless;
import at.uibk.dps.ee.enactables.serverless.ServerlessFunction;
import at.uibk.dps.ee.model.properties.PropertyServiceFunctionUser;
import at.uibk.dps.ee.model.properties.PropertyServiceResource;
import at.uibk.dps.ee.model.properties.PropertyServiceResourceServerless;
import at.uibk.dps.ee.model.properties.PropertyServiceResource.ResourceType;
import net.sf.opendse.model.Mapping;
import net.sf.opendse.model.Resource;
import net.sf.opendse.model.Task;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.spy;

import static org.junit.Assert.*;

public class ScheduleInterpreterUserTest {

  protected static class InterpreterMock extends ScheduleInterpreterUser {

    public InterpreterMock(FunctionFactoryLocal localFunctionFactory,
        FunctionFactoryServerless functionFacSl) {
      super(localFunctionFactory, functionFacSl);
    }

    @Override
    protected EnactmentFunction interpretScheduleUser(Task task,
        Set<Mapping<Task, Resource>> scheduleModel) {
      return null;
    }
  }

  @Test
  public void getFunctionForMappingTest() {/*
    Task task = PropertyServiceFunctionUser.createUserTask("task", "Addition");
    Resource local = PropertyServiceResource.createResource("res", ResourceType.Local);
    Resource serverless =
        PropertyServiceResourceServerless.createServerlessResource("res", "resLink");
    Mapping<Task, Resource> localMapping = new Mapping<Task, Resource>("local", task, local);
    Mapping<Task, Resource> serverlessMapping =
        new Mapping<Task, Resource>("serverless", task, serverless);
    LocalFunctionAbstract functionMockLockal = mock(LocalFunctionAbstract.class);
    FunctionFactoryLocal factoryMock = mock(FunctionFactoryLocal.class);
    FunctionFactoryServerless mockFacSl = mock(FunctionFactoryServerless.class);
    InterpreterMock tested = new InterpreterMock(factoryMock, mockFacSl);
    InterpreterMock spy = spy(tested);
    EnactmentFunction serverlessFunc = mock(EnactmentFunction.class);
    Mockito.doReturn(serverlessFunc).when(spy).interpretServerless(task, serverless);
    Mockito.doReturn(functionMockLockal).when(spy).interpretLocal(task, local);
    assertEquals(serverlessFunc, spy.getFunctionForMapping(serverlessMapping));
    assertEquals(functionMockLockal, spy.getFunctionForMapping(localMapping));
  */}

  @Test
  public void interpretLocalTest() {/*
    Task task = PropertyServiceFunctionUser.createUserTask("id", LocalCalculations.Addition.name());
    Resource res = new Resource("res");
    LocalFunctionAbstract functionMock = mock(LocalFunctionAbstract.class);
    FunctionFactoryLocal factoryMock = mock(FunctionFactoryLocal.class);
    when(factoryMock.getLocalFunction(LocalCalculations.Addition)).thenReturn(functionMock);
    FunctionFactoryServerless mockFacSl = mock(FunctionFactoryServerless.class);
    InterpreterMock tested = new InterpreterMock(factoryMock, mockFacSl);
    assertEquals(functionMock, tested.interpretLocal(task, res));
  */}
  
  @Test
  public void interpretServerlessTest() {/*
    Task task = PropertyServiceFunctionUser.createUserTask("task", "fancyType");
    String resLink = "link";
    Resource res = PropertyServiceResourceServerless.createServerlessResource("res", resLink);
    FunctionFactoryLocal factoryMock = mock(FunctionFactoryLocal.class);    
    FunctionFactoryServerless mockFacSl = mock(FunctionFactoryServerless.class);
    ServerlessFunction slFuncMock = mock(ServerlessFunction.class);
    when(mockFacSl.createServerlessFunction(res)).thenReturn(slFuncMock);
    InterpreterMock tested = new InterpreterMock(factoryMock, mockFacSl);
    assertEquals(slFuncMock, tested.interpretServerless(task, res));
  */}

  @Test(expected = IllegalStateException.class)
  public void interpretLocalTestWrongString() {
    Task task = PropertyServiceFunctionUser.createUserTask("id", "blabla");
    Resource res = new Resource("res");
    FunctionFactoryLocal factoryMock = mock(FunctionFactoryLocal.class);
    FunctionFactoryServerless mockFacSl = mock(FunctionFactoryServerless.class);
    InterpreterMock tested = new InterpreterMock(factoryMock, mockFacSl);
    tested.interpretLocal(task, res);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEmptyMapping() {
    Task task = new Task("task");
    Set<Mapping<Task, Resource>> schedule = new HashSet<>();
    FunctionFactoryServerless mockFacSl = mock(FunctionFactoryServerless.class);
    InterpreterMock tested = new InterpreterMock(mock(FunctionFactoryLocal.class), mockFacSl);
    tested.interpretSchedule(task, schedule);
  }
}
