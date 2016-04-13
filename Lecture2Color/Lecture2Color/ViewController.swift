//
//  ViewController.swift
//  Lecture2Color
//
//  Created by Philip Canniff on 2/5/15.
//  Copyright (c) 2015 Philip Canniff. All rights reserved.
//

import UIKit

class ViewController: UIViewController {
    
    // Hex Label
    @IBOutlet weak var hexLabel: UILabel!
    //******ColorView Outlets******
    @IBOutlet weak var FirstColorView: UIView!
    @IBOutlet weak var SecondColorView: UIView!
    
    //******Slider Outlets******
    
    @IBOutlet weak var RedSlider: UISlider!
    @IBOutlet weak var GreenSlider: UISlider!
    @IBOutlet weak var BlueSlider: UISlider!
    
    @IBOutlet weak var RedValue: UITextField!
    @IBOutlet weak var GreenValue: UITextField!
    @IBOutlet weak var BlueValue: UITextField!
    
    // Current View to Modify or Display info about
    var currentActiveView: UIView?
    
    /*Blendview Outlet Collections */
    
    @IBOutlet var BlendViewCollection: [UIView]!
    
    
    @IBOutlet var blendViewCollection_D1: [UIView]!
    
    
    @IBOutlet var blendViewCollection_D2: [UIView]!
   
    
    @IBOutlet var blendViewCollection_L1: [UIView]!
    
    
    @IBOutlet var blendViewCollection_L2: [UIView]!
    
    
    
    
    @IBAction func Slider(sender: UISlider) {
        
        if let intensities = currentActiveView?.layer.backgroundColor?.RGBA() {
        
        switch sender.tag{
            case 10:
                currentActiveView?.layer.backgroundColor = UIColor(red: CGFloat(sender.value), green: intensities.green, blue: intensities.blue, alpha: 1.0).CGColor;
            
            case 20:
                currentActiveView?.layer.backgroundColor = UIColor(red: intensities.red, green: CGFloat(sender.value), blue: intensities.blue, alpha: 1.0).CGColor;
            case 30:
                currentActiveView?.layer.backgroundColor = UIColor(red: intensities.red, green: intensities.green, blue: CGFloat(sender.value), alpha: 1.0).CGColor;
        
        default:
            print("I'll delete")
            }
            
            UpdateBlendedViews()
            updateHexLabel(intensities)
            
        }
        
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        //Add Tap Gesture Recognizers
        //Each View needs a unique gesture
        var tapGesture = UITapGestureRecognizer(target: self, action: #selector(ViewController.tapGestureRecognized(_:)))
        FirstColorView.addGestureRecognizer(tapGesture)
        tapGestureRecognized(tapGesture);
        
        
        
        tapGesture = UITapGestureRecognizer(target: self, action: #selector(ViewController.tapGestureRecognized(_:)))
        SecondColorView.addGestureRecognizer(tapGesture)
        
        // Add tap Festure Recongnizers to Blended Views
        for i in 0 ..< BlendViewCollection.count {
        
            tapGesture = UITapGestureRecognizer(target: self, action: #selector(ViewController.tapGestureRecognized(_:)))
            BlendViewCollection[i].addGestureRecognizer(tapGesture)
            tapGesture = UITapGestureRecognizer(target: self, action: #selector(ViewController.tapGestureRecognized(_:)))
            blendViewCollection_D1[i].addGestureRecognizer(tapGesture)
            tapGesture = UITapGestureRecognizer(target: self, action: #selector(ViewController.tapGestureRecognized(_:)))
            blendViewCollection_L1[i].addGestureRecognizer(tapGesture)
            tapGesture = UITapGestureRecognizer(target: self, action: #selector(ViewController.tapGestureRecognized(_:)))
            blendViewCollection_L2[i].addGestureRecognizer(tapGesture)
            tapGesture = UITapGestureRecognizer(target: self, action: #selector(ViewController.tapGestureRecognized(_:)))
            blendViewCollection_D2[i].addGestureRecognizer(tapGesture)
        
        
        
        }
        
        
        
        
        
        UpdateBlendedViews()
        
       
    }
    func tapGestureRecognized(sender: UITapGestureRecognizer) {
        
        /*Disable Slider when a blendView is a currentView*/
        if let tag = sender.view?.tag {
            
            if tag == 5 {
                RedSlider.enabled = true;
                GreenSlider.enabled = true;
                BlueSlider.enabled = true;
            
            } else {
                RedSlider.enabled = false;
                GreenSlider.enabled = false;
                BlueSlider.enabled = false;
            
            }
        
        
        }
        
        if let view = currentActiveView {
    
            view.layer.borderWidth = 0;
        }
        
    
        currentActiveView = sender.view;
        
        /**/
        currentActiveView?.layer.borderColor = UIColor.whiteColor().CGColor;
        currentActiveView?.layer.borderWidth = 3;
        
        /*Adjust slider's, Hex label, and RGB text labels*/
        
       let intensity = sender.view!.layer.backgroundColor!.RGBA()
        
        /*Update Sliders*/
        
        RedSlider.value = Float(intensity.red)
        GreenSlider.value = Float(intensity.green)
        BlueSlider.value = Float(intensity.blue)
    
        updateHexLabel(intensity)
    }
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    func updateHexLabel (colors: (red:CGFloat, green: CGFloat, blue: CGFloat, alpha: CGFloat)) {
    
        let _red = Int(colors.red * 255)
        let _green = Int(colors.green * 255)
        let _blue = Int(colors.blue * 255)
    
        let newText = "#" + String(format: "%2X", _red) + String(format: "%2X", _green) + String(format: "%2X", _blue)
        
        RedValue.text = "\(_red)"
        GreenValue.text = "\(_green)"
        BlueValue.text = "\(_blue)"
        
        let brandNewText = newText.stringByReplacingOccurrencesOfString(" ", withString: "0", options: .LiteralSearch, range: nil)
        
        hexLabel.text = brandNewText;
    }
    func UpdateBlendedViews () {
    
    /* Get Color Values fom main color Views*/
        let color1 = FirstColorView.layer.backgroundColor!.RGBA()
        let hexCode = FirstColorView.layer.backgroundColor!.HexCode()
        let color2 = SecondColorView.layer.backgroundColor!.RGBA()
        
        print(hexCode);
    /* Calculate the Delta(change in value) for each blendedview */
        let redDelta = (color1.red - color2.red) / 8
        let greenDelta = (color1.green - color2.green) / 8
        let blueDelta = (color1.blue - color2.blue) / 8
        
        
    /* Loop through and change blendviews colors */
        var _red:CGFloat = 0;
        var _blue:CGFloat = 0;
        var _green:CGFloat = 0;
        
        for i in 0 ..< BlendViewCollection.count {
        
            _red = color1.red - redDelta * CGFloat(i)
            _green = color1.green - greenDelta * CGFloat(i)
            _blue = color1.blue - blueDelta * CGFloat(i)
            
            BlendViewCollection[i].layer.backgroundColor = UIColor(red: _red, green: _green, blue: _blue, alpha: 1.0).CGColor
            
            blendViewCollection_D1[i].layer.backgroundColor = UIColor(red: _red - (_red * 0.25), green: _green - (_green * 0.25), blue: _blue - (_blue * 0.25), alpha: 1.0).CGColor
            
            blendViewCollection_D2[i].layer.backgroundColor = UIColor(red: _red - (_red * 0.5), green: _green - (_green * 0.5), blue: _blue - (_blue * 0.5), alpha: 1.0).CGColor
            
            blendViewCollection_L1[i].layer.backgroundColor = UIColor(red: _red + (_red * 0.25), green: _green + (_green * 0.25), blue: _blue + (_blue * 0.25), alpha: 1.0).CGColor
            
            blendViewCollection_L2[i].layer.backgroundColor = UIColor(red: _red + (_red * 0.5), green: _green + (_green * 0.5), blue: _blue + (_blue * 0.5), alpha: 1.0).CGColor
            
        }
    
    }
    


}

