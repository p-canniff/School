//
//  ViewController.swift
//  PCanniff_MemoryGame
//
//  Created by Philip Canniff on 2/9/15.
//  Copyright (c) 2015 Philip Canniff. All rights reserved.
//

import UIKit
class ViewController: UIViewController {
    
    //Establishing Variables
    var timer = NSTimer()
    var timeValue = 0;
    var completedMatches = 0;
    var backgroundImage = UIImage(named: "Background")
    var backgroundImage2 = UIImage(named: "Start")
    var alert = UIAlertView(title: "VICTORY!", message: "Congratulations on matching all 17!", delegate: nil, cancelButtonTitle: "Game Reset")
    
    //Array that will be shuffled and assign 'image' strings to my objects.
    var imageArray : [String] = ["Fire","Torch","Shield", "GoldBars", "Treasure", "Spear", "Katana", "Sword", "Gauntlet", "Hammer","Fire","Torch","Shield", "GoldBars", "Treasure", "Spear", "Katana", "Sword", "Gauntlet", "Hammer", "Rope", "Rope", "Target", "Target", "Bone", "Bone", "ManaPotion", "ManaPotion", "HealthPotion", "HealthPotion", "BOMB", "Eye", "Eye", "Wizard", "Wizard"]
    
    var tileArray : [MemoryTile] = []
    var compareArray : [MemoryTile] = []
    
    //Control Outlets
    @IBOutlet weak var timerLabel: UILabel!
    @IBOutlet weak var PlayLabelButton: UIButton!
    @IBOutlet weak var completedMatchesLabel: UILabel!
    
    
    //Play Button (No Pause functionality yet)
    @IBAction func PlayPause(sender: UIButton) {
        if timeValue == 0 {
            timer = NSTimer.scheduledTimerWithTimeInterval(1, target:self, selector: #selector(ViewController.update), userInfo: nil, repeats: true)
            timer.fire()
            imageArray.shuffle()
            tileRandomizer(imageArray);
            PlayLabelButton.setTitle("GO!", forState: .Normal)
        }
    }
    
    //Updating the counter with timer ticks.
    func update() {
        timerLabel.text = String(timeValue += 1)
    }
    
    //My Outlet Collection of all my Buttons
    @IBOutlet var myViews: [UIButton]!
    
    //My Action for what happens when a tile is pressed.
    @IBAction func ButtonTapped(sender: UIButton) {
      
        //Create a pause of (1) second interval in a seperate que while my tile's UI updates, and then proceeds to check them.
        let priority = DISPATCH_QUEUE_PRIORITY_DEFAULT
        dispatch_async(dispatch_get_global_queue(priority, 0)) {
            
            [NSThread .sleepForTimeInterval(NSTimeInterval(1))]
            
            dispatch_async(dispatch_get_main_queue()) {
                //To ensure triple taps get through
                if self.compareArray.count <= 2 {
                self.compare()
                }
            }
        }
        print(self.compareArray.count);
        
        
        if compareArray.count < 2 {
                self.compareArray.append(self.tileArray[sender.tag]);
        sender.setBackgroundImage(UIImage(named: self.tileArray[sender.tag].image), forState: .Normal)
        if imageArray[sender.tag] == "BOMB" {
            print("BOMB")
        }
        }
    }
    //My compare function that relates both index [0] and index [1] in my array.
    //I first check the location based on my ImageArray since it remains in the same order of my object array. 
    //I then check the objects tag, to make sure it isn't the same object
    func compare() {
        
        if compareArray.count == 2 {
            if compareArray[0].image == compareArray[1].image {
                if compareArray[0].tagID != compareArray[1].tagID {
                    //Hiding and disabling matched IDs and Image pairs.
                    compareArray[0].button.hidden = true;
                    compareArray[1].button.hidden = true;
                    compareArray[1].button.enabled = false;
                    compareArray[1].button.enabled = false;
                    //Updating complete pair counter
                    completedMatches += 1
                    completedMatchesLabel.text = "\(completedMatches)/17"
                    //reseting array for new pairs to enter
                    compareArray = []
                    //If all pairs are found:
                    if completedMatches == 17 {
                        //Stop clock, reset all the tiles, values, and buttons.
                        timer.invalidate()
                        for index in myViews {
                            index.enabled = true;
                            index.hidden = false;
                            timeValue = 0
                            PlayLabelButton.setTitle("Play", forState: .Normal)
                            completedMatches = 0
                            completedMatchesLabel.text = "\(completedMatches)/17"

                        }
                        //Reshuffle array and prepare for replay:
                        imageArray.shuffle()
                        tileRandomizer(imageArray);
                        alert.show()
                    }
                } else {
                    compareArray[0].button.setBackgroundImage(backgroundImage, forState: .Normal);
                    compareArray = [];
                }
            } else {
                compareArray[0].button.setBackgroundImage(backgroundImage, forState: .Normal);
                compareArray[1].button.setBackgroundImage(backgroundImage, forState: .Normal);
                compareArray = []
            }
        }
    }
    //Some UI changes to my Play button and Tiles
    //Updating timer with a stringValue of the current timeValue counter (0).
    override func viewDidLoad() {
        super.viewDidLoad()
        
        print(myViews.count)
        print(imageArray.count)
        timerLabel.text = String(timeValue)
        PlayLabelButton.layer.borderWidth = 1.5;
        PlayLabelButton.layer.borderColor = UIColor.whiteColor().CGColor;
        
        for index in myViews {
            
            index.layer.borderWidth = 1.5;
            index.layer.borderColor = UIColor.blackColor().CGColor
            index.layer.opacity = 0.7;
            index.setBackgroundImage(backgroundImage2, forState: .Normal);
            index.setTitle("", forState: .Normal)
            index.enabled = false;
            
            
        }
        
    }
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    //My function for creating all of my tile objects after the array of images is shuffled.
    func tileRandomizer(array : [String]) {
        tileArray = [];
        for i in 0 ..< myViews.count {
            let image = array[i]
            //Creating Object "tile" for saving tile information and enabling all of my tiles.
            let tile = MemoryTile(image: image, tagID: i, button: myViews[i])
            myViews[i].tag = i;
            myViews[i].titleForState(.Normal)
            myViews[i].setTitle("", forState: .Normal)
            myViews[i].backgroundImageForState(.Normal)
            myViews[i].setBackgroundImage(backgroundImage, forState: .Normal)
            myViews[i].enabled = true;
            //Pushing to match my already now sorted imageArray
            tileArray.append(tile);
        }
        
    }
    
}
//I created an extension here off of the data type Array so that I could create and in and out function to 'shuffle' any array.
//This is what emulates my random placements.
extension Array {
    mutating func shuffle() {
        //Looping for however much range in said Array.
        for i in 0..<(count - 1) {
            //Assigning j to a random integer that cannot be i (or it's current place in the array)
            let j = Int(arc4random_uniform(UInt32(count - i))) + i
            //Using a generic swap to replace it at it's current position with another item location.
            swap(&self[i], &self[j])
        }
    }
}
