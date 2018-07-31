import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FormucenikComponent } from './formucenik.component';

describe('FormucenikComponent', () => {
  let component: FormucenikComponent;
  let fixture: ComponentFixture<FormucenikComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FormucenikComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FormucenikComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
