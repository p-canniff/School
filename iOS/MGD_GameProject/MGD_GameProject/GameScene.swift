//
//  GameScene.swift
//  MGD_GameProject
//
//  Created by Philip Canniff on 5/10/15.
//  Copyright (c) 2015 Philip Canniff. All rights reserved.
//

import SpriteKit


class GameScene: SKScene, SKPhysicsContactDelegate {
    
    var isShooting = false
    var isRunning = false
    let girl = SKSpriteNode(imageNamed: "frame-2.png")
    let move = SKSpriteNode(imageNamed: "rightarrow")
    let pause = SKSpriteNode(imageNamed: "pause")
    var score = 0;
    var deathLabel = SKLabelNode(text: "YOU LOSE")
    let shootButton = SKSpriteNode(imageNamed: "fireButton")
    var scoreLabel = SKLabelNode(text: "Score: 0")
    var parallaxArray : [ParallaxSprite] = []
    
    struct PhysicsBank {
        
        static let None   : UInt32 = 0
        static let All    : UInt32 = UInt32.max
        static let Girl   : UInt32 = 1<<0     // 1 00000001
        static let Rat    : UInt32 = 1<<1     // 2 00000010
        static let Zombie : UInt32 = 1<<3   // 3 00001000
        static let Ground : UInt32 = 1<<2
        static let Bullet : UInt32 = 1<<4        // 4 00000100
        
    }
    
    override func didMoveToView(view: SKView) {
        
        let layer1 = ParallaxSprite(name: "bg04ground", speed: 5.0, frame: self.frame)
        layer1.zPosition = -19
        
        let layer2 = ParallaxSprite(name: "bg04tree", speed: 2.5, frame: self.frame)
        layer2.zPosition = -20
        
        let layer3 = ParallaxSprite(name: "mountains", speed: 1.75, frame: self.frame)
        layer3.zPosition = -21
    
        self.addChild(layer1)
        self.addChild(layer2)
        self.addChild(layer3)
        
        parallaxArray.append(layer1)
        parallaxArray.append(layer2)
        parallaxArray.append(layer3)
        
        let crickets = SKAction.repeatAction(SKAction.playSoundFileNamed("crickets.wav", waitForCompletion: true), count: 1)
        let freq = SKAction.repeatActionForever(crickets)
        
        let music = SKAction.repeatAction(SKAction.playSoundFileNamed("wonder.wav", waitForCompletion: true), count: 1)
        let loop = SKAction.repeatActionForever(music)
        self.runAction(loop)
        self.runAction(freq)
        
        self.physicsBody = SKPhysicsBody(edgeLoopFromRect: frame)
        self.name = "frame"
        self.physicsBody?.categoryBitMask = PhysicsBank.Ground
        
        physicsWorld.gravity = CGVectorMake(0, -9.8)
        physicsWorld.contactDelegate = self
        
        makeScene()
        makeUI()
        makeCharacter()
        makeEnemy()
        spawnBat()
        
    }
    
    override func touchesBegan(touches: Set<UITouch>, withEvent event: UIEvent?) {
        for touch in (touches ) {
            let location = touch.locationInNode(self)
            
            if !move.containsPoint(location) && !shootButton.containsPoint(location) && !pause.containsPoint(location) {
            
                spawnFire(location)
            
            }
            
            if pause.containsPoint(location){
                if self.view?.paused == true {
                    
                    self.view?.paused = false
                
                } else {
            
                self.view?.paused = true
                    
                }
            
            }
            /* Move Button Touch*/
            if move.containsPoint(location) {
                
                isRunning = true;
                moveForward()
            
            }
            /*Shoot Button Touch*/
            if shootButton.containsPoint(location) {
                
                    shootGun()
   
            }
        }
    }
    
    override func touchesEnded(touches: Set<UITouch>, withEvent event: UIEvent?) {
        for touch in (touches ) {
            let location = touch.locationInNode(self)
         
            if move.containsPoint(location) {
                
                let girlTexture1 = SKTexture(imageNamed: "frame-1")
                let girlTexture2 = SKTexture(imageNamed: "frame-2")
                let girlTexture3 = SKTexture(imageNamed: "frame-3")
                let girlTexture4 = SKTexture(imageNamed: "frame-4")
                let girlTexture5 = SKTexture(imageNamed: "frame-5")
                let girlTexture6 = SKTexture(imageNamed: "frame-6")
                let girlTexture7 = SKTexture(imageNamed: "frame-7")
                let girlTexture8 = SKTexture(imageNamed: "frame-8")
                
                let idle = SKAction.repeatActionForever(SKAction.animateWithTextures([girlTexture2, girlTexture3, girlTexture4, girlTexture5, girlTexture6, girlTexture7, girlTexture8, girlTexture1], timePerFrame: 0.2 ))
                
                isRunning = false
                
                
                girl.runAction(idle)
                
            }
        }
    }
    
    override func touchesMoved(touches: Set<UITouch>, withEvent event: UIEvent?) {
        for touch in (touches ) {
            let location = touch.locationInNode(self)
            
            if !move.containsPoint(location) && !shootButton.containsPoint(location) {
                
                let girlTexture1 = SKTexture(imageNamed: "frame-1")
                let girlTexture2 = SKTexture(imageNamed: "frame-2")
                let girlTexture3 = SKTexture(imageNamed: "frame-3")
                let girlTexture4 = SKTexture(imageNamed: "frame-4")
                let girlTexture5 = SKTexture(imageNamed: "frame-5")
                let girlTexture6 = SKTexture(imageNamed: "frame-6")
                let girlTexture7 = SKTexture(imageNamed: "frame-7")
                let girlTexture8 = SKTexture(imageNamed: "frame-8")
                
                let idle = SKAction.repeatActionForever(SKAction.animateWithTextures([girlTexture2, girlTexture3, girlTexture4, girlTexture5, girlTexture6, girlTexture7, girlTexture8, girlTexture1], timePerFrame: 0.2 ))
                
                isRunning = false
                
                girl.runAction(idle)

            
            }
        }
    }
    
    func spawnFire(location : CGPoint){
        
        let blast = SKSpriteNode(imageNamed:"Moon-Blast.png")
        let wave = SKAction.moveTo(location, duration: 0.5)
    
        blast.position = CGPointMake(self.size.width * 0.12, self.size.height * 0.45)
        blast.setScale(0.05)
        let done = SKAction.removeFromParent()
        let soundAction = SKAction.playSoundFileNamed("fireBall.wav", waitForCompletion: false)
        
        blast.runAction(SKAction.sequence([wave, done]))
        self.runAction(soundAction)
        self.addChild(blast)
    
    }
    func spawnBat(){
        
        let flapTexture1 = SKTexture(imageNamed: "flyLeft_1")
        let flapTexture2 = SKTexture(imageNamed: "flyLeft_2")
        let flapTexture3 = SKTexture(imageNamed: "flyLeft_3")
        let flapTexture4 = SKTexture(imageNamed: "flyLeft_4")
    
        let bat = SKSpriteNode(imageNamed:"flyLeft_1")
        let flap = SKAction.repeatActionForever(SKAction.animateWithTextures([flapTexture2, flapTexture3, flapTexture4, flapTexture1], timePerFrame: 0.15))
        let fly = SKAction.moveTo(CGPointMake(self.size.width * -0.1, self.size.height * 0.80), duration: 3.0)
        let done = SKAction.removeFromParent()
        
        bat.position = CGPointMake(self.size.width , self.size.height * 0.80)
        bat.setScale(0.5)
        bat.runAction(flap)
        
        bat.runAction(SKAction.sequence([fly, done]))
        
        self.addChild(bat)
    
    }

    func makeCharacter(){
        
        let girlTexture1 = SKTexture(imageNamed: "frame-1")
        let girlTexture2 = SKTexture(imageNamed: "frame-2")
        let girlTexture3 = SKTexture(imageNamed: "frame-3")
        let girlTexture4 = SKTexture(imageNamed: "frame-4")
        let girlTexture5 = SKTexture(imageNamed: "frame-5")
        let girlTexture6 = SKTexture(imageNamed: "frame-6")
        let girlTexture7 = SKTexture(imageNamed: "frame-7")
        let girlTexture8 = SKTexture(imageNamed: "frame-8")
        
        let idle = SKAction.repeatActionForever(SKAction.animateWithTextures([girlTexture2, girlTexture3, girlTexture4, girlTexture5, girlTexture6, girlTexture7, girlTexture8, girlTexture1], timePerFrame: 0.2 ))
        
        girl.position = CGPointMake(self.size.width * 0.13, self.size.height * 0.45)
        girl.setScale(0.42)
        girl.runAction(idle)
        girl.name = "mygirl"
        
        girl.physicsBody = SKPhysicsBody(rectangleOfSize: CGSize(width: girl.size.width * 0.4, height: 420))
        girl.physicsBody!.dynamic = true
        girl.physicsBody!.affectedByGravity = true
        girl.physicsBody!.mass = 0.015
        girl.physicsBody!.allowsRotation = false
        girl.physicsBody?.categoryBitMask = PhysicsBank.Girl // 3
        girl.physicsBody?.contactTestBitMask = PhysicsBank.All // 4
        girl.physicsBody?.collisionBitMask = PhysicsBank.Rat | PhysicsBank.Ground | PhysicsBank.Zombie
        
        self.addChild(girl)
        deathLabel.removeFromParent()
    
    }
    func makeScene(){
        
        let myDarkAquaColor = SKColor.clearColor()
        let groundSize = CGSizeMake(self.frame.width, self.frame.height * 0.1)
        let ground = SKSpriteNode(color: myDarkAquaColor, size: groundSize)
        ground.name = "floor"
        ground.position = CGPointMake(self.size.width * 0.5, self.size.height * 0.17)
        ground.physicsBody = SKPhysicsBody(rectangleOfSize: CGSize(width: frame.size.width, height: 0.001))
        ground.physicsBody?.categoryBitMask = PhysicsBank.Ground // 3
        ground.physicsBody?.contactTestBitMask = PhysicsBank.All // 4
        ground.physicsBody?.collisionBitMask = PhysicsBank.Rat | PhysicsBank.Girl
        ground.physicsBody?.affectedByGravity = false
        ground.physicsBody?.dynamic = false
        ground.zPosition = -10
        
        self.addChild(ground)
    }
    
    func makeEnemy(){
        
        let zombie = SKSpriteNode(imageNamed: "z1.png")

        zombie.position = CGPointMake(self.size.width * 0.9999, self.size.height * 0.48)
        zombie.setScale(0.42)
        zombie.name = "zombie"
        zombie.zPosition = -15
        
        let zTexture1 = SKTexture(imageNamed: "z1")
        let zTexture2 = SKTexture(imageNamed: "z2")
        let zTexture3 = SKTexture(imageNamed: "z3")
        let zTexture4 = SKTexture(imageNamed: "z4")
        let zTexture5 = SKTexture(imageNamed: "z5")
        let zTexture6 = SKTexture(imageNamed: "z6")
        let zTexture7 = SKTexture(imageNamed: "z7")
        let zTexture8 = SKTexture(imageNamed: "z8")
        let walking = SKAction.repeatActionForever(SKAction.animateWithTextures([zTexture2, zTexture3, zTexture4, zTexture5, zTexture6, zTexture7, zTexture8, zTexture1], timePerFrame: 0.2 ))
        let move = SKAction.moveByX(-2000.0, y: zombie.position.y, duration: 12.0)
        zombie.physicsBody = SKPhysicsBody(rectangleOfSize: CGSize(width: 40, height: 380))
        zombie.physicsBody!.dynamic = true
        zombie.physicsBody!.affectedByGravity = true
        zombie.physicsBody!.mass = 0.015
        zombie.physicsBody!.allowsRotation = false
        zombie.physicsBody?.categoryBitMask = PhysicsBank.Zombie // 3
        zombie.physicsBody?.contactTestBitMask = PhysicsBank.All // 4
        zombie.physicsBody?.collisionBitMask = PhysicsBank.Rat | PhysicsBank.Ground | PhysicsBank.Girl | PhysicsBank.Bullet
        
        self.addChild(zombie)
        
        zombie.runAction(walking)
        zombie.runAction(move)
    
    }
    func makeUI(){
    
        move.position = CGPoint(x: self.size.width * 0.86, y: self.size.height * 0.21)
        move.setScale(0.2)
        move.alpha = 1.0
        
        pause.position = CGPoint(x: self.size.width * 0.08, y: self.size.height * 0.80)
        pause.setScale(0.2)
        pause.alpha = 1.0

        scoreLabel.position = CGPoint(x: self.size.width * 0.84, y: self.size.height * 0.77)
        scoreLabel.setScale(1.75)
        scoreLabel.alpha = 1.0
        
        shootButton.position = CGPoint(x: self.size.width * 0.12, y: self.size.height * 0.21)
        shootButton.setScale(0.8)
        shootButton.alpha = 1.0
        
        self.addChild(move)
        self.addChild(pause)
        self.addChild(shootButton)
        self.addChild(scoreLabel)
    
    }
    func moveForward(){
    
        let runTexture1 = SKTexture(imageNamed: "running_1")
        let runTexture2 = SKTexture(imageNamed: "running_2")
        let runTexture3 = SKTexture(imageNamed: "running_3")
        let runTexture4 = SKTexture(imageNamed: "running_4")
        let runTexture5 = SKTexture(imageNamed: "running_5")
        let runTexture6 = SKTexture(imageNamed: "running_6")
        
        let running = SKAction.repeatActionForever(SKAction.animateWithTextures([runTexture1, runTexture2, runTexture3, runTexture4, runTexture5, runTexture6], timePerFrame: 0.15, resize: true, restore: true))
        
        girl.runAction(running)
    
    }
    func shootGun(){
    
        girl.texture = SKTexture(imageNamed: "shoot1")
        
        let shootTexture1 = SKTexture(imageNamed: "shoot1")
        let shootTexture2 = SKTexture(imageNamed: "shoot2")
        let shootTexture3 = SKTexture(imageNamed: "shoot3")
        let shootTexture4 = SKTexture(imageNamed: "shoot4")
        
        let shoot = SKAction.animateWithTextures([shootTexture1, shootTexture2, shootTexture3, shootTexture4], timePerFrame: 0.125, resize: true, restore: true)
        let soundAction = SKAction.playSoundFileNamed("gunshot.mp3", waitForCompletion: false)
        self.runAction(soundAction)
        
        
        let aim = SKAction.moveTo(CGPointMake(self.size.width * 1.1, self.size.height * 0.44), duration: 1.0)
        let done = SKAction.removeFromParent()
        
        let bullet = SKSpriteNode(imageNamed:"bullet")
        bullet.name = "bullet"
        bullet.physicsBody = SKPhysicsBody(rectangleOfSize: CGSize(width: 40, height: 380))
        bullet.physicsBody!.dynamic = true
        bullet.physicsBody!.affectedByGravity = true
        bullet.physicsBody!.mass = 0.015
        bullet.physicsBody!.allowsRotation = false
        bullet.physicsBody?.categoryBitMask = PhysicsBank.Bullet // 3
        bullet.physicsBody?.contactTestBitMask = PhysicsBank.All // 4
        bullet.physicsBody?.collisionBitMask = PhysicsBank.Zombie
        
        bullet.position = CGPointMake(self.size.width * 0.19, self.size.height * 0.5)
        bullet.setScale(0.05)
        
        bullet.runAction(SKAction.sequence([aim, done]))
        
        self.addChild(bullet)
        
        girl.runAction(shoot)
        
    }
    func didBeginContact(contact: SKPhysicsContact) {
        
        print(contact.bodyA.node?.name!)
        print(contact.bodyB.node?.name!)
        
        if contact.bodyA.node?.name! == "zombie" || contact.bodyB.node?.name! == "zombie" {
            if contact.bodyA.node?.name! == "bullet" || contact.bodyB.node?.name! == "bullet" {
                
                enemyDeath(contact.bodyA.node!.position)
                contact.bodyA.node?.removeFromParent()
                contact.bodyB.node?.removeFromParent()
                score = score + 10
                
                makeEnemy()
            }
        }
        if contact.bodyA.node?.name! == "zombie" || contact.bodyB.node?.name! == "zombie" {
            if contact.bodyA.node?.name! == "mygirl" || contact.bodyB.node?.name! == "mygirl" {
                
                enemyDeath(contact.bodyA.node!.position)
                contact.bodyA.node?.removeFromParent()
                contact.bodyB.node?.removeFromParent()
                score = 0
                
                
                
                deathLabel.position = CGPoint(x: self.size.width * 0.5, y: self.size.height * 0.60)
                deathLabel.setScale(1.75)
                deathLabel.alpha = 1.0
                
                self.addChild(deathLabel)
                
                
                let wait = SKAction.waitForDuration(5.0)
                
                self.runAction(wait, completion: { () -> Void in
                    self.makeCharacter()
                    self.makeEnemy()
                })
                
            }
        }
    }
    
    func enemyDeath(position: CGPoint){
    
        var airTexture1 = SKTexture(imageNamed: "airCloud1")
        let airTexture2 = SKTexture(imageNamed: "airCloud2")
        let airTexture3 = SKTexture(imageNamed: "airCloud3")
        let airTexture4 = SKTexture(imageNamed: "airCloud4")
        let airTexture5 = SKTexture(imageNamed: "airCloud5")
        
        let airCloud = SKSpriteNode(imageNamed: "airCloud1")
        airCloud.position = position
        airCloud.setScale(0.1)
        
        let airExplode = SKAction.animateWithTextures([airTexture2, airTexture3, airTexture4, airTexture5], timePerFrame: 0.15)
        let done = SKAction.removeFromParent()
        self.addChild(airCloud)
        
        airCloud.runAction(SKAction.sequence([airExplode, done]))
    
    }
    override func update(currentTime: CFTimeInterval) {
        
        scoreLabel.text = "Score: \(score)"
        
        if isRunning == true {
        for p : ParallaxSprite in parallaxArray {
            p.update();
            
            }
        }
    }
}
