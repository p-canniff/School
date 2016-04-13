//
//  TilesClass.swift
//  PCanniff_MemoryGame
//
//  Created by Philip Canniff on 2/11/15.
//  Copyright (c) 2015 Philip Canniff. All rights reserved.
//

import Foundation
import UIKit



class MemoryTile {
    
    var image : String
    var tagID : Int
    var button : UIButton
    
    init(){
        self.image =  ""
        self.tagID = -1
        self.button = UIButton()
        
    }
    
    
    init(image : String, tagID: Int, button : UIButton){
        self.image = image;
        self.tagID = tagID
        self.button = button
    
    }




}