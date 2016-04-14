//
//  SKParallaxScrolling.swift
//  MGD_GameProject
//
//  Created by Philip Canniff on 5/13/15.
//  Copyright (c) 2015 Philip Canniff. All rights reserved.
//
import SpriteKit

class ParallaxSprite: SKSpriteNode {
    
    var parallaxSpeed : Double = 0.0;
    var parallaxName : String = "";
    var image1 : SKSpriteNode!
    var image2 : SKSpriteNode!
    
    required init(coder: NSCoder) {
        fatalError("NSCoding not supported")
    }
    
    override init(texture: SKTexture?, color: UIColor, size: CGSize) {
        super.init(texture: texture, color: color, size:size)
    }
    
    init(name : String, speed : Double, frame: CGRect) {
        self.parallaxName = name;
        self.parallaxSpeed = speed;
        let texture : SKTexture = SKTexture(imageNamed: parallaxName);
        
        
        super.init(texture: texture, color:SKColor.blackColor(), size: CGSizeMake(100, 100));
        
        self.image1 = SKSpriteNode(texture: texture, size: texture.size());
        self.image1.position = CGPointMake(CGRectGetMidX(frame), CGRectGetMidY(frame) + 385);
        self.addChild(image1);
        
        if(speed > 0) {
            self.image2 = SKSpriteNode(texture: texture);
            self.image2.position.x = self.image1.position.x + self.image1.size.width;
            self.image2.position.y = self.image1.position.y;
            self.addChild(self.image2);
        }
    }
    
    func update() {
        if(self.parallaxSpeed > 0){
            self.position.x -= CGFloat(self.parallaxSpeed);
            
            if(self.position.x < -self.image1.size.width) {
                self.position.x = 0;
            }
        }
    }
}