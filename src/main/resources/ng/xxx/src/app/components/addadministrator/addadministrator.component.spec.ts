import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddadministratorComponent } from './addadministrator.component';

describe('AddadministratorComponent', () => {
  let component: AddadministratorComponent;
  let fixture: ComponentFixture<AddadministratorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddadministratorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddadministratorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
