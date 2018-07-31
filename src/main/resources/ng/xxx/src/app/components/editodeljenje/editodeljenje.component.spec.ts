import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditodeljenjeComponent } from './editodeljenje.component';

describe('EditodeljenjeComponent', () => {
  let component: EditodeljenjeComponent;
  let fixture: ComponentFixture<EditodeljenjeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditodeljenjeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditodeljenjeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
