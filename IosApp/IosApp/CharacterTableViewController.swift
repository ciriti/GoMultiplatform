//
//  CharacterTableViewController
//  AppIos
//
//  Created by Carmelo Iriti on 01.09.19.
//  Copyright © 2019 Carmelo Iriti. All rights reserved.
//

import UIKit
import SDWebImage
import commoncode

typealias CommonResult = commoncode.Result
class CharacterTableViewController: UIViewController {
    
    @IBOutlet var characters: UICollectionView!
    
    var charactersList = [CommonResult]()
    var nextPage : Int64 = 1
    
    
    let uc = UseCaseMpKt.createUseCaseMp()
    let uc1 = UseCaseMpCompanion().create()
    
    var idle : Bool = true
    var closure = {}

    override func viewDidLoad() {
        super.viewDidLoad()
        
        characters.dataSource = self
        characters.register(UINib.init(nibName: "CharacterCell", bundle: nil), forCellWithReuseIdentifier: "MovieCell")
        characters.delegate = self
        characters.reloadData()
        loadPage(page: nextPage)
    }
    
    func loadPage(page : Int64){
        uc.getCharacters(
            page: 1 ,
            success: {elements in
                self.charactersList = elements.results
                self.characters.reloadData()
        },
            failure: {elements in print(elements)}
        )
    }

}

extension CharacterTableViewController: UICollectionViewDelegateFlowLayout {
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        let padding: CGFloat =  10
        let collectionViewSize = collectionView.frame.size.width - padding
        return CGSize(width: collectionViewSize/2, height: collectionViewSize/2)
    }
}

extension CharacterTableViewController: UICollectionViewDataSource {
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return charactersList.count
    }

    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "MovieCell", for: indexPath) as! CharacterCell
        let character = charactersList[indexPath.row]
        cell.setup(_character: character)
        print("index element: " + String(indexPath.row))
        return cell
    }

    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {

    }
}

func getPercent(count : Int, current : Int) -> Int{
    return (100 * current) / count
}


class CharacterCell: UICollectionViewCell {
    @IBOutlet weak var status: UILabel!
    @IBOutlet weak var storedLocationLabel: UILabel!
    @IBOutlet weak var posterView: UIImageView!
    @IBOutlet weak var rankLabel: UILabel!
    @IBOutlet weak var titleLabel: UILabel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }
    
    func setup(_character: CommonResult) {
        titleLabel.text = _character.name
        status.text = _character.status
        posterView.sd_setImage(with: URL(string: _character.image))
    }
}
