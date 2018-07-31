import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ListucenikComponent } from './listucenik.component';

describe('ListucenikComponent', () => {
  let component: ListucenikComponent;
  let fixture: ComponentFixture<ListucenikComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ListucenikComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ListucenikComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
