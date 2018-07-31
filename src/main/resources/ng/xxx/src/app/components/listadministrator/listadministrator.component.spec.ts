import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ListadministratorComponent } from './listadministrator.component';

describe('ListadministratorComponent', () => {
  let component: ListadministratorComponent;
  let fixture: ComponentFixture<ListadministratorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ListadministratorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ListadministratorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
