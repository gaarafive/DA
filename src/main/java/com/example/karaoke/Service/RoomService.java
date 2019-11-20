package com.example.karaoke.Service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.karaoke.Repository.RoomRepository;
import com.example.karaoke.entity.Food;
import com.example.karaoke.entity.Room;

@Service
public class RoomService {
	@Autowired
	RoomRepository repo;
	public List<Room> getAll() {
		return repo.findAll();
	}
	public Room save(Room room) {
		return repo.save(room);
	}
	public void remove(int id) {
		repo.deleteById(id);
	}
	public Room getById(int id) {
		Optional<Room> f = repo.findById(id);
		if(f.isPresent()) {
			return f.get();
		}
		return null;
	}
	public boolean UploadImage(MultipartFile file, String url) {
        File uploadRootDir = new File(url);
        if (!uploadRootDir.exists()) {
            uploadRootDir.mkdirs();
        }
        String name = file.getOriginalFilename();

        if (name != null && name.length() > 0) {
            try {
                File serverFile = new File(uploadRootDir.getAbsolutePath() + File.separator + name);
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                stream.write(file.getBytes());
                stream.close();
            } catch (Exception e) {
            	return false;
            }
        }
		return true;
	}
	
	public List<Room> findRoomWithIsActive(boolean isactive){
		return repo.findRoomWithIsActive(isactive);
	}
	public List<Room> findByNameContaining(String value){
		return repo.findByNameContaining(value);
	}
}
