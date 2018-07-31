import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FormadministratorComponent } from './formadministrator.component';

describe('FormadministratorComponent', () => {
  let component: FormadministratorComponent;
  let fixture: ComponentFixture<FormadministratorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FormadministratorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FormadministratorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
