package org.openlmis.equipment.repository;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

import org.mockito.runners.MockitoJUnitRunner;
import org.openlmis.db.categories.UnitTests;
import org.openlmis.equipment.domain.MaintenanceRequest;
import org.openlmis.equipment.repository.mapper.MaintenanceRequestMapper;


@Category(UnitTests.class)
@RunWith(MockitoJUnitRunner.class)
public class MaintenanceRequestRepositoryTest {

  @Mock
  MaintenanceRequestMapper mapper;

  @InjectMocks
  MaintenanceRequestRepository repository;


  @Test
  public void shouldGetById() throws Exception {
    repository.getById(1L);
    verify(mapper).getById(1L);
  }

  @Test
  public void shouldGetAllForFacility() throws Exception {
    repository.getAllForFacility(5L);
    verify(mapper).getAllForFacility(5L);
  }

  @Test
  public void shouldGetAllForVendor() throws Exception {
    repository.getAllForVendor(5L);
    verify(mapper).getAllForVendor(5L);
  }

  @Test
  public void shouldGetOutstandingForVendor() throws Exception {
    repository.getOutstandingForVendor(5L);
    verify(mapper).getOutstandingRequestsForVendor(5L);
  }

  @Test
  public void shouldGetOutstandingForUser() throws Exception {
    repository.getOutstandingForUser(5L);
    verify(mapper).getOutstandingRequestsForUser(5L);
  }

  @Test
  public void shouldGetAll() throws Exception {
    repository.getAll();
    verify(mapper).getAll();
  }

  @Test
  public void shouldInsert() throws Exception {
    MaintenanceRequest request = new MaintenanceRequest();
    repository.insert(request);
    verify(mapper).insert(request);
  }

  @Test
  public void shouldUpdate() throws Exception {
    MaintenanceRequest request = new MaintenanceRequest();
    repository.update(request);
    verify(mapper).update(request);
  }

  @Test
  public void shouldGetFullHistory() throws Exception {
    repository.getFullHistory(7L);
    verify(mapper).getFullHistory(7L);
  }
}