//
//  ColorExtensions.swift
//  Lecture2Color
//
//  Created by Philip Canniff on 2/12/15.
//  Copyright (c) 2015 Philip Canniff. All rights reserved.
//

import Foundation
import UIKit

extension UIColor {

    func RGBA() -> (red: CGFloat, green: CGFloat, blue: CGFloat, alpha: CGFloat){
    
        //An array of CGFloats
        let intensities = CGColorGetComponents(self.CGColor);
        //Returning Tuple
        return (intensities[0], intensities[1], intensities[2], intensities[3]);
    
    
    }

}

extension CGColor {
    
    func RGBA() -> (red: CGFloat, green: CGFloat, blue: CGFloat, alpha: CGFloat){
        
        //An array of CGFloats
        let intensities = CGColorGetComponents(self);
        //Returning Tuple
        return (intensities[0], intensities[1], intensities[2], intensities[3]);
        
        
    }
    func HexCode() -> String{
        
        //An array of CGFloats
        let intensities = CGColorGetComponents(self);
        
        let colors = self.RGBA()
        
        let _red = Int(colors.red * 255)
        let _green = Int(colors.green * 255)
        let _blue = Int(colors.blue * 255)
        
        let newText = "#" + String(format: "%2X", _red) + String(format: "%2X", _green) + String(format: "%2X", _blue)
        
         let brandNewText = newText.stringByReplacingOccurrencesOfString(" ", withString: "0", options: .LiteralSearch, range: nil)
        
        return brandNewText;
        
        
    }
    
}
