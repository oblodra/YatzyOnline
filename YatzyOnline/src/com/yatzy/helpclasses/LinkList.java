package com.yatzy.helpclasses;

public class LinkList {
	
	private Link first;
	private int bestMatch;
	private int bestMaxScore;
	private int bestMaxCurrentScore;
	private int nbrOfLinks;
	
	public LinkList() {
		this.first = null;
		this.nbrOfLinks = 0;
	}
	
	public boolean isEmpty() {
		return (this.first == null);
	}
	
	//moves the sizeOfSet amount of Link to the front based on number of matches
	public int sortBestMatch(int sizeOfSet) {
		
		//om antal links är mindre eller lika med sizeOfSet skippas sortering
		if (this.nbrOfLinks <= sizeOfSet) {
			return this.nbrOfLinks;
		}
		// antal Links är större än sizeOfSet så sizeOfSet Links baserat av sortingskriteriet är flyttat till början av listan
		else {
			Link current, previous, prevMaxItem, maxItem, startItem;
			
			//om först har bestMatch så skippa att hitta bestmatch
			if (first.getMatch() == this.bestMatch) {
				startItem = first;
				current = first;
				previous = first;
			}
			else {	
				current = first.getNext();
				previous = first;
				//om man för nån anledning inte hittar bestMatch tas sista Link i listan
				while (current.getMatch() != this.bestMatch && current.getNext() != null) {
					previous = current;
					current = current.getNext();
				}
				startItem = current;
				previous.setNext(current.getNext());
				startItem.setNext(first.getNext());
				first = startItem;
			}
			int max;
			for (int i = 0; i< sizeOfSet-1; i++) {
				max = startItem.getNext().getMatch(); // eftersom jag vet att antal Links är större än antal Links jag vill sortera så är Link efter denna alltid skiljt från null
				
				current = startItem.getNext();
				previous = startItem;
				prevMaxItem = startItem;
				maxItem = startItem.getNext();
	
				while (current.getNext() != null) {
					previous = current;
					current = current.getNext();
					if (current.getMatch() > max) {
						max = current.getMatch();
						prevMaxItem = previous;
						maxItem = current;
					}
				}
				//no need to move Link if no new is found
				if (prevMaxItem == startItem) {
					startItem = maxItem;
				}
				else {
					prevMaxItem.setNext(maxItem.getNext());
					maxItem.setNext(startItem.getNext());
					startItem.setNext(maxItem);
					startItem = maxItem;
				}
				
			}
			return sizeOfSet;
		}	
	}
	
	//moves the sizeOfSet amount of Link to the front based on number of max score
	public int sortBestMaxScore(int sizeOfSet) {
		//om antal links är mindre eller lika med sizeOfSet skippas sortering
				if (this.nbrOfLinks <= sizeOfSet) {
					return this.nbrOfLinks;
				}
				// antal Links är större än sizeOfSet så sizeOfSet Links baserat av sortingskriteriet är flyttat till början av listan
				else {
					Link current, previous, prevMaxItem, maxItem, startItem;
					
					//om först har bestMatch så skippa att hitta bestmatch
					if (first.getMaxPoints() == this.bestMaxScore) {
						startItem = first;
						current = first;
						previous = first;
					}
					else {	
						current = first.getNext();
						previous = first;
						//om man för nån anledning inte hittar bestMatch tas sista Link i listan
						while (current.getMaxPoints() != this.bestMaxScore && current.getNext() != null) {
							previous = current;
							current = current.getNext();
						}
						startItem = current;
						previous.setNext(current.getNext());
						startItem.setNext(first.getNext());
						first = startItem;
					}
					int max;
					for (int i = 0; i< sizeOfSet-1; i++) {
						max = startItem.getNext().getMaxPoints(); // eftersom jag vet att antal Links är större än antal Links jag vill sortera så är Link efter denna alltid skiljt från null
						
						current = startItem.getNext();
						previous = startItem;
						prevMaxItem = startItem;
						maxItem = startItem.getNext();
			
						while (current.getNext() != null) {
							previous = current;
							current = current.getNext();
							if (current.getMaxPoints() > max) {
								max = current.getMaxPoints();
								prevMaxItem = previous;
								maxItem = current;
							}
						}
						//no need to move Link if no new is found
						if (prevMaxItem == startItem) {
							startItem = maxItem;
						}
						else {
							prevMaxItem.setNext(maxItem.getNext());
							maxItem.setNext(startItem.getNext());
							startItem.setNext(maxItem);
							startItem = maxItem;
						}
						
					}
					return sizeOfSet;
				}
	}
	
	//moves the sizeOfSet amount of Link to the front based on number of current score
	public int sortBestCurrentScore(int sizeOfSet) {
		//om antal links är mindre eller lika med sizeOfSet skippas sortering
				if (this.nbrOfLinks <= sizeOfSet) {
					return this.nbrOfLinks;
				}
				// antal Links är större än sizeOfSet så sizeOfSet Links baserat av sortingskriteriet är flyttat till början av listan
				else {
					Link current, previous, prevMaxItem, maxItem, startItem;
					
					//om först har bestMatch så skippa att hitta bestmatch
					if (first.getCurrentPoints() == this.bestMaxCurrentScore) {
						startItem = first;
						current = first;
						previous = first;
					}
					else {	
						current = first.getNext();
						previous = first;
						//om man för nån anledning inte hittar bestMatch tas sista Link i listan
						while (current.getCurrentPoints() != this.bestMaxCurrentScore && current.getNext() != null) {
							previous = current;
							current = current.getNext();
						}
						startItem = current;
						previous.setNext(current.getNext());
						startItem.setNext(first.getNext());
						first = startItem;
					}
					int max;
					for (int i = 0; i< sizeOfSet-1; i++) {
						
						max = startItem.getNext().getCurrentPoints(); // eftersom jag vet att antal Links är större än antal Links jag vill sortera så är Link efter denna alltid skiljt från null
						
						current = startItem.getNext();
						previous = startItem;
						prevMaxItem = startItem;
						maxItem = startItem.getNext();
			
						while (current.getNext() != null) {
							previous = current;
							current = current.getNext();
							if (current.getCurrentPoints() > max) {
								max = current.getCurrentPoints();
								prevMaxItem = previous;
								maxItem = current;
							}
						}
						//no need to move Link if no new is found
						if (prevMaxItem == startItem) {
							startItem = maxItem;
						}
						else {
							prevMaxItem.setNext(maxItem.getNext());
							maxItem.setNext(startItem.getNext());
							startItem.setNext(maxItem);
							startItem = maxItem;
						}
						
					}
					return sizeOfSet;
				}
	}
	
	public void insertFirst(int[] data, int match, int maxpoints, int currentpoints, int index) {
		Link newLink = new Link(data, match, maxpoints, currentpoints, index);
		if (isEmpty()) {
			newLink.setNext(null);
		}
		else {
			newLink.setNext(first);
		}
		//uppdaterar maxvärde
		if (this.bestMatch < match)
			this.bestMatch = match;
		if (this.bestMaxScore < maxpoints)
			this.bestMaxScore = maxpoints;
		if (this.bestMaxCurrentScore < currentpoints)
			this.bestMaxCurrentScore = currentpoints;
		
		first = newLink;
		this.nbrOfLinks++;
	}
	
	// assumes LinkList isn't empty!!!!
	public Link deleteFirst() {
		Link temp = first;
		first = first.getNext();
		this.nbrOfLinks--;
		return temp;
	}
	
	public void displayList() {
		System.out.println("first --> Last");
		Link current = first;
		while(current != null) {
			current.display();
			current = current.getNext();
		}
		System.out.println("");
	}
	
	public int getNbrOfLinks() {
		return this.nbrOfLinks;
	}
	
	// clear the list, leave all links to garbage collector
	public void clearList() {
		this.first = null;
		this.bestMatch = 0;
		this.bestMaxScore = 0;
		this.bestMaxCurrentScore = 0;
		this.nbrOfLinks = 0;
	}


}
