import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FormnastavnikComponent } from './formnastavnik.component';

describe('FormnastavnikComponent', () => {
  let component: FormnastavnikComponent;
  let fixture: ComponentFixture<FormnastavnikComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FormnastavnikComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FormnastavnikComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
